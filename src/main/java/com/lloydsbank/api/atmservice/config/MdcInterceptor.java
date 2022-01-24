package com.lloydsbank.api.atmservice.config;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * The type Mdc interceptor.
 */
public class MdcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        MDC.put("Correlation-Id", getCorrelationId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove("Correlation-Id");
    }

    private String getCorrelationId() {
        return UUID.randomUUID().toString();
    }
}