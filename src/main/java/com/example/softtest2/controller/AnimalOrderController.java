package com.example.softtest2.controller;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.service.AnimalOrderProcessingService;
import com.example.softtest2.service.AnimalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class AnimalOrderController {

    private final AnimalOrderService animalOrderService;

    private final AnimalOrderProcessingService animalOrderProcessingService;

//    public AnimalOrderController(AnimalOrderService animalOrderService) {
//        this.animalOrderService = animalOrderService;
//    }


    @PostMapping("/quickorder")
    public OrderStatus createQuickOrder(@RequestParam long animalId, @RequestParam int quantity) {
        return animalOrderService.createQuickOrder(animalId, quantity);
    }


    @PostMapping("/processorder")
    public OrderStatus processOrder(@RequestParam long orderId) {
        return animalOrderProcessingService.processOrder(orderId);
    }









    ///////////////////////////////////////////////////////////////

    // Select all animal orders
    @GetMapping("/list")
    public List<AnimalOrderEntity> getAllAnimalOrders() {
        return animalOrderService.getAllAnimalOrders();
    }

    // Select animal orders by animal name
    @GetMapping("/byanimalid")
    public List<AnimalOrderEntity> getAnimalOrdersByAnimalID(Long animalId) {
        //return animalOrderService.getAnimalOrdersByAnimal(animal);
        return animalOrderService.getAnimalOrdersByAnimalId(animalId);
    }

    // Select animal order by id
    @GetMapping("/byid")
    public AnimalOrderEntity getAnimalOrderById(Long id) {
        return animalOrderService.getAnimalOrderById(id);
    }

    // Create a new animal order
    @PostMapping("/create")
    public AnimalOrderEntity createAnimalOrder(@RequestBody AnimalOrderEntity animalOrderEntity) {
        return animalOrderService.createAnimalOrder(animalOrderEntity);
    }

    // Update animal order by id
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<?> updateAnimalOrder(@PathVariable Long id, @RequestBody AnimalOrderEntity updatedAnimalOrder) {
        try {
            animalOrderService.updateAnimalOrder(id, updatedAnimalOrder);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete animal order by id
    @DeleteMapping("/delete/{id}")
    public void deleteAnimalOrder(@PathVariable Long id) {
        animalOrderService.deleteAnimalOrder(id);
    }



}
