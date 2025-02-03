package com.example.demo.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

// This object is responsible for transporting data returned on Http
@Data
@Builder
public class ResponsePayload {
    private String message;
    private HttpStatus status;
    private Object data;
}
