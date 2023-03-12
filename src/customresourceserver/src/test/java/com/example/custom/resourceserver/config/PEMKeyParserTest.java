package com.example.custom.resourceserver.config;

import com.example.custom.resourceserver.verification.Base64PEMKeyData;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PEMKeyParserTest {


    @Test
    public void shouldParsePemKeyData() throws IOException {
        String modulus = "s9IvhlvbCs3-8sX9VPHjMlefPTZm6NeUDCAsD5HT-q91NP2ECiGnWxxBk5dnSkQwjskbgneIxfAtdLziYIMjUcUU2eHMcUxMMi7XdmGNrepSnq12tmkb2LMaZVkQ7xIIJVkLe-AzwxzPV_pAEqXSVsdUC7OZhOp9Ob8tZ5cp8dOOyOvWMhs_r1cY7pFaYliqclIpWcVrndZnN-ojbHm1IiqaLtEA9BoDnCTP2YSpj83F0hI5JMMBgZYUDuupJN8eBBZCKsLLRzckZO4q9X_Z59jaImPT48d7nZ97mGzEVOoh9xL_XX8c1oPIJ0dIMPkAXGyC8DJxCASkkeAs3bWgFw";
        String exponent = "AQAB";
        URL resource = Resources.getResource("certs-payload.json");
        String certPayload = Resources.toString(resource, StandardCharsets.UTF_8);

        //when
        Base64PEMKeyData parse = new PEMKeyParser().parse(certPayload);

        //then
        assertEquals(modulus, parse.modulus());
        assertEquals(exponent, parse.exponent());


    }

}