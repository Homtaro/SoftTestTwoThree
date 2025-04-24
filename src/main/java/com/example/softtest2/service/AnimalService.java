package com.example.softtest2.service;

import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.repository.AnimalRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepo animalRepo;

    @Autowired
    public AnimalService(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }


    //Select *
    public List<AnimalEntity> getAllAnimals() {
        return animalRepo.findAll();
    }

    //Select with specific animal
    public List<AnimalEntity> getAnimalsByAnimal(String animal) {
        return animalRepo.findByAnimal(animal);
    }

    //Select by ID
    public AnimalEntity getAnimalById(Long id) {
        return animalRepo.findById(id).orElse(null);
    }

    //Create a new animal
    public AnimalEntity createAnimal(AnimalEntity animalEntity) {
        return animalRepo.save(animalEntity);
    }

    //Update animal by id
    @Transactional
    public void updateAnimal(Long id, AnimalEntity updatedAnimal) {
        animalRepo.findById(id)
                .ifPresent(animal -> {
                    animal.setAnimal(updatedAnimal.getAnimal());
                    animal.setQuantity(updatedAnimal.getQuantity());
                    animal.setDescription(updatedAnimal.getDescription());
                    animalRepo.save(animal);
                });
    }

    //Delete animal by id
    public void deleteAnimal(Long id) {
        animalRepo.deleteById(id);
    }



}
