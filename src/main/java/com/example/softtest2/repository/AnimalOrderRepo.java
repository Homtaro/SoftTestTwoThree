package com.example.softtest2.repository;

import com.example.softtest2.entity.AnimalOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalOrderRepo extends JpaRepository<AnimalOrderEntity, Long> {

    //Select *
    List<AnimalOrderEntity> findAll();

    //Select with specific animal
    List<AnimalOrderEntity> findByAnimalId(Long animalId);

    //Select by ID
    Optional<AnimalOrderEntity> findById(Long aLong);

    //Select all orders for a specific user
    List<AnimalOrderEntity> findByUserId(Long userId);

}