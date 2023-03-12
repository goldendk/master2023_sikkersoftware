package com.example.custom.resourceserver.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

/**
 * Responsible for verifying the signature and content of a JWT token sent from a user request.
 * <p>
 * Assumptions: http request using client-credentials grant.
 */
public class JWTTokenVerifier {
    private static Logger logger = LoggerFactory.getLogger(JWTTokenVerifier.class);

    private boolean validToken;
    private String bearerToken;
    private PublicKey publicKey;

    /**
     * Parses the supplied token and sets the outcome which can be fetched using {@link #isValidToken()}
     */
    public void parseToken() throws Exception {
        checkPreconditions();


        String jwt = bearerToken;
        String signedData = jwt.substring(0, jwt.lastIndexOf("."));
        String signatureB64u = jwt.substring(jwt.lastIndexOf(".") + 1, jwt.length());
        byte signature[] = Base64.getUrlDecoder().decode(signatureB64u);


        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signedData.getBytes());
        boolean isVerify = sig.verify(signature);

        validToken = isVerify;
    }

    public static PublicKey getPublicKey(String modulus, String exponent) {
        try {
            byte exponentB[] = Base64.getUrlDecoder().decode(exponent);
            byte modulusB[] = Base64.getUrlDecoder().decode(modulus);
            BigInteger bigExponent = new BigInteger(1, exponentB);
            BigInteger bigModulus = new BigInteger(1, modulusB);

            PublicKey publicKey;
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(bigModulus, bigExponent));
            return publicKey;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchSecretKey() {
        return null;
    }

    private void checkPreconditions() {
        if (bearerToken == null) {
            throw new IllegalStateException("authorizationHeader must be set before parsing");
        }
    }

    public boolean isValidToken() {
        return validToken;
    }

    public void setBearerToken(String authorization) {
        this.bearerToken = authorization;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
