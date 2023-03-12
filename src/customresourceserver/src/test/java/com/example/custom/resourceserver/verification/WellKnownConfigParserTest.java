package com.example.custom.resourceserver.verification;

import com.example.custom.resourceserver.verification.jwks.JwksData;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class WellKnownConfigParserTest {

    @Test
    public void shouldParsePayload() throws IOException {
        WellKnownConfigParser parser = new WellKnownConfigParser();
        WellKnownConfigParser.class.getResource("/well-known-payload.json");

        String payload = Resources.toString(Resources.getResource("well-known-payload.json"), StandardCharsets.UTF_8);
        assertNotNull(payload);

        JwksData parse = parser.parse(payload);

        assertEquals("http://localhost:8080/realms/dev/protocol/openid-connect/certs", parse.uri());
    }
}