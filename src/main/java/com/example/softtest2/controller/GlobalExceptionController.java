package com.example.softtest2.controller;

import com.example.softtest2.dto.ErrorDTO;
import com.example.softtest2.dto.ValidationErrorDTO;
import com.example.softtest2.exception.CustomException;
import com.example.softtest2.model.ErrorType;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.TreeMap;

@ControllerAdvice
public class GlobalExceptionController {

    /*public ResponseEntity<ErrorDTO> handleCustomException(CustomException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(ex.getErrorCode());
        errorDTO.setErrorMessage(ex.getErrorMessage());
        errorDTO.setTimestamp(OffsetDateTime.now());

        //I kinda dont want to create 3 handlers for 3 different exceptions
        // Maybe map will help

        //HttpStatus status =

        return new ResponseEntity<>(errorDTO, HttpStatus.OK);

    }*/

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorDTO> handleBusinessException(CustomException ex) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorType(ErrorType.BUSINESS);

        errorDTO.setErrorCode(ex.getErrorCode().name());
        errorDTO.setErrorMessage(ex.getErrorCode().getDescription());

        errorDTO.setTimestamp(OffsetDateTime.now());

//        HttpStatus status = mapErrorTypeToHttpStatus(errorDTO.getErrorType());
        HttpStatus status = HttpStatus.OK;


        return new ResponseEntity<>(errorDTO, status);

    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDTO> handleTechnicalException(RuntimeException ex) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorType(ErrorType.TECHNICAL);
        errorDTO.setErrorMessage(ex.getMessage());
        errorDTO.setTimestamp(OffsetDateTime.now());

        HttpStatus status = mapErrorTypeToHttpStatus(errorDTO.getErrorType());

        return new ResponseEntity<>(errorDTO, status);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorDTO> handleValidationException(ConstraintViolationException ex) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorType(ErrorType.VALIDATION);
        errorDTO.setErrorMessage(ex.getMessage());
        errorDTO.setTimestamp(OffsetDateTime.now());

        HttpStatus status = mapErrorTypeToHttpStatus(errorDTO.getErrorType());

        return new ResponseEntity<>(errorDTO, status);

    }

   /* @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ValidationErrorDTO> handleValidationException(ConstraintViolationException ex) {

        ValidationErrorDTO errorDTO = new ValidationErrorDTO();
        errorDTO.setTimestamp(OffsetDateTime.now());

        errorDTO.setConstraints(new TreeMap<>());
        ex.getConstraintViolations().forEach(
                constraintViolation -> {
                    String fieldName = constraintViolation.getPropertyPath().toString();
                    String errorMessage = constraintViolation.getMessage();
                    errorDTO.getConstraints().put(fieldName, errorMessage);
                }
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorType(ErrorType.TECHNICAL);
        errorDTO.setErrorCode("UNKNOWN_ERROR");
        errorDTO.setErrorMessage("An unexpected error occurred");
        errorDTO.setTimestamp(OffsetDateTime.now());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }






    // Mapper Error Code to Error type
    private ErrorType mapErrorCodeToErrorType(String errorCode) {
        if (errorCode == null) {
            return ErrorType.TECHNICAL;
        }
        if (errorCode.startsWith("VAL_")) {
            return ErrorType.VALIDATION;
        } else if (errorCode.startsWith("BUS_")) {
            return ErrorType.BUSINESS;
        } else if (errorCode.startsWith("TECH_")) {
            return ErrorType.TECHNICAL;
        } else {
            return ErrorType.TECHNICAL;
        }
    }

    // Mapper Error Types to HttpStatus
    private HttpStatus mapErrorTypeToHttpStatus(ErrorType errorType) {
        return switch (errorType) {
            case VALIDATION -> HttpStatus.BAD_REQUEST;
            case BUSINESS -> HttpStatus.CONFLICT;
//            case BUSINESS -> HttpStatus.UNAUTHORIZED;
            case TECHNICAL -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
