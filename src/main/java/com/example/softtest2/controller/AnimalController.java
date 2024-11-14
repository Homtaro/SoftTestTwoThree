package com.example.softtest2.controller;


import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Select all animals
    @GetMapping("/list")
    public List<AnimalEntity> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // Select animals by animal name
    @GetMapping("/byanimal")
    public List<AnimalEntity> getAnimalsByAnimal(String animal) {
        return animalService.getAnimalsByAnimal(animal);
    }

    // Select animal by id
    @GetMapping("/byid")
    public AnimalEntity getAnimalById(Long id) {
        return animalService.getAnimalById(id);
    }

    // Create a new animal
    @PostMapping("/create")
    public AnimalEntity createAnimal(@RequestBody AnimalEntity animalEntity) {
        return animalService.createAnimal(animalEntity);
    }

    // Update animal by id
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<?> updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity updatedAnimal) {
        try {
            animalService.updateAnimal(id, updatedAnimal);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete animal by id
    @DeleteMapping("/delete/{id}")
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }



}
