package com.example.custom.resourceserver.config;

import com.example.custom.resourceserver.verification.JWTTokenVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2Interceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        JWTTokenVerifier verifier = new JWTTokenVerifier();
        verifier.setAuthorizationHeader(request.getHeader("Authorization"));
        verifier.parseToken();

        if (!verifier.isValidToken()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token not valid!");
            return false;
        } else {
            return true;
        }
    }

}
