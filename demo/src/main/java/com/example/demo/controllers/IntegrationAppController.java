package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ResponsePayload;
import com.example.demo.payload.request.integration_app.IntegrationAppTokenRequest;
import com.example.demo.services.integration_app.IIntegrationAppService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@AllArgsConstructor
@CrossOrigin(value = "*")
@Log
@RestController
@RequestMapping("/api/integration-app")
public class IntegrationAppController {
    private final IIntegrationAppService integrationAppService;

    @PostMapping("/get-generated-token")
    public ResponseEntity<ResponsePayload> getGeneratedToken(@RequestBody IntegrationAppTokenRequest request) {
        ResponsePayload responsePayload = integrationAppService.getGeneratedToken(request);
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }
}
