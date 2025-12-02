package com.mpp.pharmacy.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.mpp.pharmacy.Exception.AuthException;
import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String headerName;
    private final String apiKey;
    private final HandlerExceptionResolver resolver;

    public ApiKeyAuthFilter(String headerName, String apiKey, HandlerExceptionResolver resolver) {
        this.headerName = headerName;
        this.apiKey = apiKey;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestApiKey = request.getHeader(headerName);

        if (apiKey.equals(requestApiKey)) {
            var authentication = new UsernamePasswordAuthenticationToken("api-user", null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            resolver.resolveException(request, response, null, new AuthException("Invalid key used " + requestApiKey));
        }
    }
}
