package com.example.recyclemarketback.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public class CustomException extends NoSuchElementException {
    private final int errorCode;
    public CustomException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
