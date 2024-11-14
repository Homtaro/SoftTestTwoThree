package com.example.softtest2.repository;

import com.example.softtest2.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepo extends JpaRepository<AnimalEntity, Long> {

    //Select *
    List<AnimalEntity> findAll();

    //Select with specific animal
    List<AnimalEntity> findByAnimal(String animal);

    //Select by ID
    Optional<AnimalEntity> findById(Long aLong);

}
