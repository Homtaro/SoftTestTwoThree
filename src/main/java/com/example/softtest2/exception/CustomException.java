package com.example.softtest2.exception;

import com.example.softtest2.model.ErrorCode;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class CustomException extends RuntimeException {

    /*private final String errorCode;
    private final String errorMessage;
    private final OffsetDateTime timestamp;

    public CustomException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = OffsetDateTime.now();
    }

    public CustomException(String errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = OffsetDateTime.now();
    }*/

    private final ErrorCode errorCode;
    private final OffsetDateTime timestamp;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.timestamp = OffsetDateTime.now();
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.timestamp = OffsetDateTime.now();
    }



}
