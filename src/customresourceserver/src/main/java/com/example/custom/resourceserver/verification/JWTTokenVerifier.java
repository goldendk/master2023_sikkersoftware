package com.example.custom.resourceserver.verification;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * Responsible for verifying the signature and content of a JWT token sent from a user request.
 * <p>
 * Assumptions: http request using client-credentials grant.
 */
public class JWTTokenVerifier {
    private static Logger logger = LoggerFactory.getLogger(JWTTokenVerifier.class);

    private boolean validToken;
    private String authorizationHeader;

    /**
     * Parses the supplied token and sets the outcome which can be fetched using {@link #isValidToken()}
     */
    public void parseToken() throws Exception {
        checkPreconditions();
        String[] split = authorizationHeader.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(split[0]));
        String payload = new String(decoder.decode(split[1]));
        SignatureAlgorithm sa = HS256;
        String secretKey = fetchSecretKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        String tokenWithoutSignature = split[0] + "." + split[1];
        String signature = split[2];

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

        if (!validator.isValid(tokenWithoutSignature, signature)) {
            validToken = false;
        }

    }

    private String fetchSecretKey() {
        return null;
    }

    private void checkPreconditions() {
        if (authorizationHeader == null) {
            throw new IllegalStateException("authorizationHeader must be set before parsing");
        }
    }

    public boolean isValidToken() {
        return validToken;
    }

    public void setAuthorizationHeader(String authorization) {
        this.authorizationHeader = authorization;
    }
}
