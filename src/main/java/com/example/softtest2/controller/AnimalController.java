package com.example.softtest2.controller;


import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
@Tag(name = "A - Animal Warehouse", description = "Controller for Animal Warehouse")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Select all animals
    @Operation(summary = "Endpoint for getting all animals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/list")
    public List<AnimalEntity> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // Select animals by animal name
    @Operation(summary = "Endpoint for getting animals by animal name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(responseCode = "404", description = "Animal not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @GetMapping("/byanimal")
    public List<AnimalEntity> getAnimalsByAnimal(
            @Parameter(description = "Animal name", required = true)
            @RequestParam String animal) {
        return animalService.getAnimalsByAnimal(animal);
    }

    // Select animal by id
    @Operation(summary = "Endpoint for getting animal by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Animal not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/byid")
    public AnimalEntity getAnimalById(
            @Parameter(description = "Animal ID", required = true)
            @RequestParam Long id) {
        return animalService.getAnimalById(id);
    }

    // Create a new animal
    @Operation(summary = "Endpoint for creating a new animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/create")
    public AnimalEntity createAnimal(
            @Parameter(description = "Animal details", required = true)
            @RequestBody AnimalEntity animalEntity) {
        return animalService.createAnimal(animalEntity);
    }

    // Update animal by id
    @Operation(summary = "Endpoint for updating an animal by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal updated successfully."),
            @ApiResponse(responseCode = "404", description = "Animal not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<?> updateAnimal(
            @Parameter(description = "Animal ID as path", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated animal details", required = true)
            @RequestBody AnimalEntity updatedAnimal) {
        try {
            animalService.updateAnimal(id, updatedAnimal);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete animal by id
    @Operation(summary = "Endpoint for deleting an animal by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Animal not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteAnimal(
            @Parameter(description = "Animal ID as path", required = true)
            @PathVariable Long id) {
        animalService.deleteAnimal(id);
    }



}
