package com.example.custom.resourceserver.config;

import com.example.custom.resourceserver.verification.Base64PEMKeyData;
import com.example.custom.resourceserver.verification.JWTTokenVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2Interceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Interceptor.class);
    private final Oauth2ConfigRetriever configRetriever;

    public OAuth2Interceptor(Oauth2ConfigRetriever oauth2ConfigRetriever) {
        this.configRetriever = oauth2ConfigRetriever;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Bearer token not found !");
        }

        Base64PEMKeyData pemKeyData = configRetriever.retrieveOauth2Config();
        String bearerTokenValue = authorization.replace("Bearer ", "");
        JWTTokenVerifier verifier = new JWTTokenVerifier();
        verifier.setBearerToken(bearerTokenValue);
        verifier.setPublicKey(JWTTokenVerifier.getPublicKey(pemKeyData.modulus(), pemKeyData.exponent()));

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
