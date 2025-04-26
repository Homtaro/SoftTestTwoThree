package com.example.softtest2.controller;

import com.example.softtest2.dto.AnimalOrderDTO;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.service.AnimalOrderProcessingService;
import com.example.softtest2.service.AnimalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

// Standart quick order creation
//    @PostMapping("/quickorder")
//    public OrderStatus createQuickOrder(@RequestParam long animalId, @RequestParam int quantity) {
//        return animalOrderService.createQuickOrder(animalId, quantity);
//    }

    //Created to fix tests error, quantity less than zero etc
    //Created a new issue, even tho data isnt being written to the DB, ID still increments
    /*@PostMapping("/quickorder")
    public ResponseEntity<String> createQuickOrder(@RequestParam Long animalId, @RequestParam int quantity) {
        try {
            OrderStatus order = animalOrderService.createQuickOrder(animalId, quantity);
            return ResponseEntity.ok("Order: " + order.toString());
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("CHK_QUANTITY_GREATER_THAN_ZERO")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity value invalid.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data.");
        }
    }*/

    //Fixed by adding a check for quantity value before creating an order
    //Not clean solution, needs refactoring, but thats for later.
    @PostMapping("/quickorder")
    public ResponseEntity<String> createQuickOrder(@RequestParam Long animalId, @RequestParam int quantity, @RequestParam Long userId) {
        try {
            if (quantity <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity value invalid.");
            }
            OrderStatus order = animalOrderService.createQuickOrder(animalId, quantity, userId);
            return ResponseEntity.ok("Order: " + order.toString());
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("CHK_QUANTITY_GREATER_THAN_ZERO")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity value invalid.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data.");
        }
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

    //DTO STUFF

    @GetMapping("/listdto")
    public List<AnimalOrderDTO> getAllAnimalOrdersDTO() {
        return animalOrderService.getOrdersDTO();
    }






    //DTO STUFF END


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

    // Select all orders for a specific user
    @GetMapping("/byuserid")
    public List<AnimalOrderEntity> getAnimalOrdersByUserId(Long userId) {
        return animalOrderService.getAnimalOrdersByUserId(userId);
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
