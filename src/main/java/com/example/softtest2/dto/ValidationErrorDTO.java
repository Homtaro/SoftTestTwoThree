package com.example.softtest2.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorDTO {

    private OffsetDateTime timestamp;

    private Map<String, String> constraints;



}
