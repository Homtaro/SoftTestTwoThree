package com.example.softtest2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error-runtime")
@Tag(name = "R - Runtime Error", description = "Controller for Runtime Error [Unchecked]")
public class ErrorRuntimeController {


    @GetMapping("/throw")
    public void throwException() throws Exception {
        throw new Exception("Runtime error");
    }



}
