package com.example.softtest2.service;


import com.example.softtest2.entity.UserEntity;
import com.example.softtest2.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    //DTO STUFF

    public UserEntity toDTO(UserEntity userEntity) {

        UserEntity dto = new UserEntity();
        dto.setId(userEntity.getId());
        dto.setName(userEntity.getName());
        dto.setSurname(userEntity.getSurname());
        dto.setPhoneNumber(userEntity.getPhoneNumber());
        dto.setAddress(userEntity.getAddress());

        return dto;

    }


    //DTO STUFF END




    ///////---SELECT---////////

    //Select *

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    //Select user by ID

    public UserEntity getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    //Select user by phone number

    public List<UserEntity> getUserByPhoneNumber(String phoneNumber) {
        return userRepo.findByPhoneNumber(phoneNumber);
    }

    //Select user by address

    public List<UserEntity> getUserByAddress(String address) {
        return userRepo.findByAddress(address);
    }

    //Select user by name

    public List<UserEntity> getUserByName(String name) {
        return userRepo.findByName(name);
    }

    //Select user by surname

    public List<UserEntity> getUserBySurname(String surname) {
        return userRepo.findBySurname(surname);
    }


    //Select user by name + surname
    public List<UserEntity> getUserByNameAndSurname(String name, String surname) {
        return userRepo.findByNameAndSurname(name, surname);
    }

    ////////---SELECT END---////////


    // Create a new user

    public UserEntity createUser(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    // Update user by id

    @Transactional
    public void updateUser(Long id, UserEntity updatedUser) {
        userRepo.findById(id)
                .ifPresent(user -> {
                    user.setName(updatedUser.getName());
                    user.setSurname(updatedUser.getSurname());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setAddress(updatedUser.getAddress());
                    userRepo.save(user);
                });
    }


    // Delete user by id

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }



}
