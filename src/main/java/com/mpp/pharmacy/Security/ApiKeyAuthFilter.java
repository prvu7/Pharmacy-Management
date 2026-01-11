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

import java.io.IOException;
import java.util.ArrayList;

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
            resolver.resolveException(request, response, null, new AuthException("Invalid API Key"));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || 
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/api-docs") ||
               path.equals("/swagger-ui.html");
    }
}
