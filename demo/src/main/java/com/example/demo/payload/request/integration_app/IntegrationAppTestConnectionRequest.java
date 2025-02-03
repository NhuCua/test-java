package com.example.demo.payload.request.integration_app;

import lombok.Data;

@Data
public class IntegrationAppTestConnectionRequest {
    private String integrationKey;
    private String token;
}
