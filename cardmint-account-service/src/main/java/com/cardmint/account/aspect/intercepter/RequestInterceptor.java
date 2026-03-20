package com.cardmint.account.aspect.intercepter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.cardmint.account.constants.AppConstants.*;


@Component
@Slf4j
public class RequestInterceptor extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = extractOrGenerateCorrelationId(request);
        String externalRefId = request.getHeader(HEADER_EXTERNAL_REF_ID);

        try {
            // Put values in MDC
            MDC.put(MDC_CORRELATION_ID, correlationId);

            if (externalRefId != null && !externalRefId.isBlank()) {
                MDC.put(MDC_EXTERNAL_REF_ID, externalRefId);
            }

            // Always return correlationId in response
            response.setHeader(HEADER_CORRELATION_ID, correlationId);

            filterChain.doFilter(request, response);

        } finally {
            MDC.clear(); // Prevent thread-local memory leak
        }
    }

    private String extractOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(HEADER_CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return correlationId;

    }
}
