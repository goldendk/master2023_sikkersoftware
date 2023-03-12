package com.example.custom.resourceserver.config;

import com.example.custom.resourceserver.verification.Base64PEMKeyData;
import com.example.custom.resourceserver.verification.WellKnownConfigParser;
import com.example.custom.resourceserver.verification.jwks.JwksData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Oauth2ConfigRetriever {


    private String issuerUrl;
    private String wellKnownPath;

    private Base64PEMKeyData cachedData = null;

    /**
     * Retrieves configuration assuming this format:
     *
     * <pre>
     * oauth2:
     *   jwt:
     *     issuer-uri: http://localhost:8080/realms/dev
     *     well-known-config-path: /.well-known/openid-configuration
     *
     * </pre>
     */

    public Oauth2ConfigRetriever(@Value("${oauth2.jwt.issuer-uri}") String issuerUri,
                                 @Value("${oauth2.jwt.well-known-config-path}") String wellKnownPath) {
        this.issuerUrl = issuerUri;
        this.wellKnownPath = wellKnownPath;
    }

    public Base64PEMKeyData retrieveOauth2Config() throws URISyntaxException, IOException, InterruptedException {

        if (cachedData != null) {
            return cachedData;
        }

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest issuerUrlRequest = HttpRequest.newBuilder().GET().uri(new URI(issuerUrl + wellKnownPath))
                .build();

        HttpResponse<String> wellKnownResponse = client.send(issuerUrlRequest, HttpResponse.BodyHandlers.ofString());
        String body = wellKnownResponse.body();

        JwksData jwksData = new WellKnownConfigParser().parse(body);

        HttpRequest certsRequest = HttpRequest.newBuilder().GET().uri(new URI(jwksData.uri())).build();

        HttpResponse<String> certsResponse = client.send(certsRequest, HttpResponse.BodyHandlers.ofString());

        Base64PEMKeyData keyInfo = new PEMKeyParser().parse(certsResponse.body());

        cachedData = keyInfo;
        return cachedData;
    }

}
