package com.example.softtest2.dto;

import lombok.Data;

@Data
public class AnimalOrderDTO {

    private Long id;
    private Long animalId;
    private String animalName;
    private Long userId;
    private String userName;
    private Integer quantity;
    private String status;




}
