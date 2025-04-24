package com.example.softtest2.controller;


import com.example.softtest2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.softtest2.entity.UserEntity;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Select all users
    @GetMapping("/search/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // Select user by ID
    @GetMapping("/search/id")
    public UserEntity getUserById(Long id) {
        return userService.getUserById(id);
    }

    // Create a new user
    @GetMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    // Update user by ID
    @GetMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        try{
            userService.updateUser(id, updatedUser);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user by ID
    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }






}
