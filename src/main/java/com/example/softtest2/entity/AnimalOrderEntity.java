package com.example.softtest2.entity;

import com.example.softtest2.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animals_orders")
public class AnimalOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @Column(name = "animal", nullable = false)
//    private String animal;
    @Column(name = "animal_id", nullable = false)
    private Long animalId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "animal_id", nullable = false, referencedColumnName = "id")
//    private AnimalEntity animalEntity;


}
