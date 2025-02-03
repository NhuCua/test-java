package com.example.demo.services.integration_app;

import com.example.demo.models.ResponsePayload;
import com.example.demo.payload.request.integration_app.IntegrationAppTestConnectionRequest;
import com.example.demo.payload.request.integration_app.IntegrationAppTokenRequest;

public interface IIntegrationAppService {
    ResponsePayload getGeneratedToken(IntegrationAppTokenRequest request);

    ResponsePayload testConnection(IntegrationAppTestConnectionRequest request);
}
