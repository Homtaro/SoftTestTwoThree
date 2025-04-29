package com.example.softtest2.dto;

import com.example.softtest2.model.ErrorType;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ErrorDTO {

    private ErrorType errorType;
    private String errorCode;
    private String errorMessage;
    private OffsetDateTime timestamp;


}
