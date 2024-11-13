package com.example.softtest2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "animals_warehouse")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal", nullable = false)
    private String animal;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

//    @OneToMany(mappedBy = "animalEntity")
//    private List<AnimalOrderEntity> orders;

}
