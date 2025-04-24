package com.example.softtest2.repository;

import com.example.softtest2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    // Select all users
    List<UserEntity> findAll();

    // Select user by ID
    Optional<UserEntity> findById(Long id);

    // Select user by name
    List<UserEntity> findByName(String name);

    // Select user by surname
    List<UserEntity> findBySurname(String surname);

    //Select user by name and surname
    List<UserEntity> findByNameAndSurname(String name, String surname);


    // Select user by phone number
    List<UserEntity> findByPhoneNumber(String phoneNumber);


    // Select user by address
    List<UserEntity> findByAddress(String address);

}
