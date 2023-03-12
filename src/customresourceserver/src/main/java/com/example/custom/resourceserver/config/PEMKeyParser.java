package com.example.custom.resourceserver.config;

import com.example.custom.resourceserver.verification.Base64PEMKeyData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicReference;

public class PEMKeyParser {


    public Base64PEMKeyData parse(String body) throws JsonProcessingException {


        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(body);

        AtomicReference<JsonNode> sha256Node = new AtomicReference<>();
        jsonNode.get("keys").forEach(child -> {
            if (child.has("alg") && child.get("alg").asText().equals("RS256")) {
                sha256Node.set(child);
            }
        });

        if (sha256Node.get() == null) {
            throw new IllegalArgumentException("SHA256 key info not found");
        }

        String modulus = sha256Node.get().get("n").asText();
        String exponent = sha256Node.get().get("e").asText();

        return new Base64PEMKeyData(modulus, exponent);
    }
}
