package com.example.softtest2.controller;


import com.example.softtest2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.softtest2.entity.UserEntity;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "B - Users", description = "Controller for users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Select all users
    @Operation(summary = "Endpoint for getting all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/search/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // Select user by ID
    @Operation(summary = "Endpoint for getting user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/search/id")
    public UserEntity getUserById(
            @Parameter(description = "User ID", required = true)
            @RequestParam Long id) {
        return userService.getUserById(id);
    }

    // Create a new user
    @Operation(summary = "Endpoint for creating a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(responseCode = "409", description = "Conflict: User already exists.")
    })
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(
            @Parameter(description = "User details", required = true)
            @RequestBody UserEntity user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(201).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(409).body("User already exists");
        }
    }

    // Update user by ID
    @Operation(summary = "Endpoint for updating user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(
            @Parameter(description = "User ID as path", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated user details", required = true)
            @RequestBody UserEntity updatedUser) {
        try{
            userService.updateUser(id, updatedUser);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user by ID
    @Operation(summary = "Endpoint for deleting user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteUser(
            @Parameter(description = "User ID as path", required = true)
            @PathVariable Long id) {
        userService.deleteUser(id);
    }






}
