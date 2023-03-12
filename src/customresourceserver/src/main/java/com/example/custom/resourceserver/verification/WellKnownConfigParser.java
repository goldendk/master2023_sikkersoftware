package com.example.custom.resourceserver.verification;

import com.example.custom.resourceserver.verification.jwks.JwksData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WellKnownConfigParser {
    private static final String JWKS_URI = "jwks_uri";

    public JwksData parse(String wellKnownPayload) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.reader().readTree(wellKnownPayload);
            String jwksUri = readMandatoryString(root, JWKS_URI);
            return new JwksData(jwksUri);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private String readMandatoryString(JsonNode parent, String propertyName) {
        if (!parent.has(propertyName)) {
            throw new IllegalArgumentException("Well-known payload must have " + propertyName);
        }

        JsonNode jsonNode = parent.get(propertyName);
        if (!jsonNode.isTextual()) {
            throw new IllegalArgumentException(propertyName + " is not a textual node.");
        }
        String value = jsonNode.asText();


        return value;
    }
}
