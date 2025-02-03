package com.example.demo.services.integration_app.impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.models.ResponsePayload;
import com.example.demo.payload.request.integration_app.IntegrationAppTokenRequest;
import com.example.demo.services.BaseService;
import com.example.demo.services.integration_app.IIntegrationAppService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;

@Log
@Service
public class IntegrationAppService extends BaseService implements IIntegrationAppService {

    @Value("${integration.app.workspace.secret}")
    private String workspaceSecret;

    @Value("${integration.app.workspace.key}")
    private String workspaceKey;

    @Override
    public ResponsePayload getGeneratedToken(IntegrationAppTokenRequest request) {
        try {
            return createResponsePayload("Generate Integration App Token successfully.", HttpStatus.OK,
                    generateToken(request));
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
            return createResponsePayload("Generate Integration App Token failed.", HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    private String generateToken(IntegrationAppTokenRequest request) {
        // Create JWT claims
        Map<String, Object> tokenData = Map.of(
                "id", request.getOrganizationId().toString(), // Ensure UUID is stored as a String
                "name", request.getOrganizationName());

        // Generate secret key
        byte[] secretKeyBytes = workspaceSecret.getBytes(StandardCharsets.UTF_8);
        Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        // Generate JWT token
        return Jwts.builder()
                .claims(tokenData)
                .issuer(workspaceKey)
                .expiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000)) // 2 hours
                .signWith(secretKey) // No need to specify algorithm, it's inferred from the key
                .compact();
    }
}
