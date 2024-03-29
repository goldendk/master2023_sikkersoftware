package com.example.custom.resourceserver.verification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JWTTokenVerifierTest {

    private final String tokenToVerify = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzTHRCZXY0N0pJc1N6QVdRNkFKSVFGRmJOdV90NGhDUC1GMXYtSld4RkVJIn0.eyJleHAiOjE2Nzg2Mzk4OTYsImlhdCI6MTY3ODYzOTU5NiwianRpIjoiNTVjMTljZWQtMTY3Ny00M2M3LWE0ZDgtNzJjMDIzOTEzMTMyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNTBkNzRmMTctODNjNi00ODAxLWFkOGQtOTU0YmM0YjViMzM2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibXktY2xpZW50IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWRldiIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im15LWN1c3RvbS1jbGllbnQtc2NvcGUgcHJvZmlsZSBlbWFpbCIsImNsaWVudElkIjoibXktY2xpZW50IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTI3LjAuMC4xIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LW15LWNsaWVudCIsImNsaWVudEFkZHJlc3MiOiIxMjcuMC4wLjEifQ.gDzUSOEMj891TvARYjLJacmKdHeYvpFMlbs2_M93dN91SR5ZGtGxhOYHaro1bz5xeHdMngE91JbB_S115SJ9XrQowXmARVyMgLBj2EHV5Eb6pFpRB8KDkikplUXxr58WYcdqaDsg21DatVNyq4KtM7cSU_TqUyhKkWgpnAQv3gHV5Bgv9igCpS0SpouFZ8Qj0Ko-1jZ90O2CBWQAi9mJDzgP3ROxQx0vPa9ZLY09H3kLkXVyPLwcqw4cqCQRaSR0H7xwKDdV1XnLscOB_lP0Rv-rty7kzTkZ4a4quyA83KAZFodhAOe2KfNRSN9tf6jADwGMFLG3gz6nwMusjyTjww";

    //from boot spring
    private final String pemModulus = "s9IvhlvbCs3-8sX9VPHjMlefPTZm6NeUDCAsD5HT-q91NP2ECiGnWxxBk5dnSkQwjskbgneIxfAtdLziYIMjUcUU2eHMcUxMMi7XdmGNrepSnq12tmkb2LMaZVkQ7xIIJVkLe-AzwxzPV_pAEqXSVsdUC7OZhOp9Ob8tZ5cp8dOOyOvWMhs_r1cY7pFaYliqclIpWcVrndZnN-ojbHm1IiqaLtEA9BoDnCTP2YSpj83F0hI5JMMBgZYUDuupJN8eBBZCKsLLRzckZO4q9X_Z59jaImPT48d7nZ97mGzEVOoh9xL_XX8c1oPIJ0dIMPkAXGyC8DJxCASkkeAs3bWgFw";
    private final String pemExponent = "AQAB";

    @Test
    public void shouldVerifyToken() throws Exception {

        JWTTokenVerifier verifier = new JWTTokenVerifier();
        verifier.setBearerToken(tokenToVerify);
        verifier.setPublicKey(JWTTokenVerifier.getPublicKey(pemModulus, pemExponent));

        verifier.parseToken();
        assertTrue(verifier.isValidToken());
    }

    @Test
    public void shouldFailDueToBadModulus() throws Exception {
        JWTTokenVerifier verifier = new JWTTokenVerifier();
        verifier.setBearerToken(tokenToVerify);

        //replace part of RSA modulus and see that we can no longer verify the token. Alternatively alter sections of the signature in the
        // JWT token above.
        verifier.setPublicKey(JWTTokenVerifier.getPublicKey("abc" + pemModulus.substring(3), pemExponent));

        verifier.parseToken();
        assertFalse(verifier.isValidToken());
    }

}