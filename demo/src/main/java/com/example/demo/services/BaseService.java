package com.example.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.models.ResponsePayload;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class BaseService {
    public ResponsePayload createResponsePayload(String message, HttpStatus status, Object data) {
        return ResponsePayload.builder().message(message).status(status).data(data).build();
    }
}
