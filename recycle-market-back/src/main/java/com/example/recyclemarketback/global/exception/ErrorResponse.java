package com.example.recyclemarketback.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
}
