package com.example.softtest2.controller;


import com.example.softtest2.exception.CustomException;
import com.example.softtest2.model.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/error")
@Validated
@Tag(name = "E - Error", description = "Controller for error handling")
public class ErrorController {

    @GetMapping("/throw")
    @Operation(summary = "Endpoint for throwing exceptions")
    public void throwException(@RequestParam("exception-type") String exceptionType) {
        switch (exceptionType.toUpperCase()) {
            case "BUSINESS" -> throw new CustomException(ErrorCode.ANIMAL_NOT_FOUND);
            case "VALIDATION" -> {
                Set<ConstraintViolation<Object>> violations = new HashSet<>();
                throw new jakarta.validation.ConstraintViolationException(
                        "Validation failed: [specific reason]", violations);
            }
            case "TECHNICAL" -> throw new RuntimeException("Technical error");
            default -> throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }
    }


}
