package com.example.custom.resourceserver.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       response.getWriter().write("JWT check not implemented");


       return false;
    }

}
