package com.mpp.pharmacy.Loggers;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class TraceIdFilter implements Filter {

    private final CustomLogger logger = CustomLogger.getInstance();
    private static final String TRACE_ID_KEY = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String traceId = null;
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                traceId = httpRequest.getHeader("X-Trace-ID");
            }
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }
            
            MDC.put(TRACE_ID_KEY, traceId);

            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                logger.info(LogType.NOTIFICATION, "Request received: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
            }

            chain.doFilter(request, response);
        } finally {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                logger.info(LogType.NOTIFICATION, "Request finished: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
            }
            MDC.remove(TRACE_ID_KEY);
        }
    }
}