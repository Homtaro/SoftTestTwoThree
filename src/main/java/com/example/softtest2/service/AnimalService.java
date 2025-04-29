package com.example.softtest2.service;

import com.example.softtest2.dto.AnimalDTO;
import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.exception.CustomException;
import com.example.softtest2.model.ErrorCode;
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


    //DTO STUFF

    public AnimalEntity toDTO(AnimalEntity animalEntity) {

        AnimalEntity dto = new AnimalEntity();
        dto.setId(animalEntity.getId());
        dto.setAnimal(animalEntity.getAnimal());
        dto.setQuantity(animalEntity.getQuantity());
        dto.setDescription(animalEntity.getDescription());

        return dto;

    }




    //DTO STUFF END

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
    /*@Transactional
    public void updateAnimal(Long id, AnimalEntity updatedAnimal) {
        animalRepo.findById(id)
                .ifPresent(animal -> {
                    animal.setAnimal(updatedAnimal.getAnimal());
                    animal.setQuantity(updatedAnimal.getQuantity());
                    animal.setDescription(updatedAnimal.getDescription());
                    animalRepo.save(animal);
                });
    }*/

    /*@Transactional
    public void updateAnimal(Long id, AnimalEntity updatedAnimal) {
        animalRepo.findById(id)
                .ifPresentOrElse(
                        animal -> {
                            animal.setAnimal(updatedAnimal.getAnimal());
                            animal.setQuantity(updatedAnimal.getQuantity());
                            animal.setDescription(updatedAnimal.getDescription());
                            animalRepo.save(animal);
                        },
                        () -> {
                            throw new CustomException(ErrorCode.ANIMAL_NOT_FOUND);
                        }
                );
    }*/

    @Transactional
    public void updateAnimal(String id, AnimalDTO updatedAnimal) {
        Long animalId = Long.valueOf(id);
        animalRepo.findById(animalId)
                .ifPresentOrElse(
                        animal -> {
                            animal.setAnimal(updatedAnimal.getAnimal());
                            animal.setQuantity(updatedAnimal.getQuantity());
                            animal.setDescription(updatedAnimal.getDescription());
                            animalRepo.save(animal);
                        },
                        () -> {
                            throw new CustomException(ErrorCode.ANIMAL_NOT_FOUND);
                        }
                );
    }

    //Delete animal by id
    public void deleteAnimal(Long id) {
        animalRepo.deleteById(id);
    }



}
