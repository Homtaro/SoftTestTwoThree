package com.example.softtest2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController()
@RequestMapping("/swagger")
@Tag(name = "W - Swagger", description = "Controller for Swagger UI")
public class SwaggerController {

    @Operation(summary = "Helper endpoint for Swagger UI")
    @GetMapping("/")
    public String index() {
        return "Swagger UI is available at /swagger-ui/index.html";
    }

    @Operation(summary = "Custom endpoint for Swagger UI - returns custom HTML page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Swagger UI page served successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(responseCode = "404", description = "Swagger UI page not found.")
    })
    @GetMapping(value = "/custom", produces = MediaType.TEXT_HTML_VALUE)
    public String serveSwaggerUI() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/swagger-ui.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }


}
