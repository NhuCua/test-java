package com.example.demo.payload.request.integration_app;

import lombok.Data;

@Data
public class IntegrationAppTokenRequest {
    private String organizationId;
    private String organizationName;
}
