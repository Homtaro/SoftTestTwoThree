package com.example.softtest2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    ANIMAL_NOT_FOUND("Animal not found"),
    USER_NOT_FOUND("User not found"),
    ORDER_NOT_FOUND("Order not found"),
    DATA_NOT_FOUND("Data not found");

    @Getter
    private final String description;

}

// Question: what if we tie error type to error code?
// Example ANIMAL_NOT_FOUND(Type:BUSINESS, DESCRIPTION: Animal not found)
// Would that be better?


// Can we identify the error type by description of error code?
// Example: if description contains "not found" then type is BUSINESS
// This is not a great idea, but hey.