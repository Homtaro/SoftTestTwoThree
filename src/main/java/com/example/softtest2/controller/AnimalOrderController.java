package com.example.softtest2.controller;

import com.example.softtest2.dto.AnimalOrderDTO;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.service.AnimalOrderProcessingService;
import com.example.softtest2.service.AnimalOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
@Tag(name = "C - Animal Orders", description = "Controller for orders from animal warehouse")
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

    @Operation(summary = "Endpoint for creating a quick order",
            description = "Creates a quick order for an animal with the specified ID, quantity and user ID are required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided."),
            @ApiResponse(responseCode = "404", description = "Animal or user not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(responseCode = "409", description = "Conflict: Order already exists.")
    })
    @PostMapping("/quickorder")
    public ResponseEntity<String> createQuickOrder(
            @Parameter(description = "Animal ID", required = true)
            @RequestParam Long animalId,
            @Parameter(description = "Quantity of animal", required = true)
            @RequestParam int quantity,
            @Parameter(description = "User (Customer) ID", required = true)
            @RequestParam Long userId) {
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


    @Operation(summary = "Endpoint for processing an order",
            description = "Processes an order with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order processed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid order ID provided."),
            @ApiResponse(responseCode = "404", description = "Order not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/processorder")
    public OrderStatus processOrder(
            @Parameter(description = "Order ID", required = true)
            @RequestParam long orderId) {
        return animalOrderProcessingService.processOrder(orderId);
    }


    ///////////////////////////////////////////////////////////////

    // Select all animal orders
    @Operation(summary = "Endpoint for getting all animal orders",
            description = "Returns a list of all animal orders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animal orders retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/list")
    public List<AnimalOrderEntity> getAllAnimalOrders() {
        return animalOrderService.getAllAnimalOrders();
    }

    //DTO STUFF

    @Operation(summary = "Endpoint for getting all animal orders as DTOs",
            description = "Returns a list of all animal orders with extended information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animal orders DTOs retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/listdto")
    public List<AnimalOrderDTO> getAllAnimalOrdersDTO() {
        return animalOrderService.getOrdersDTO();
    }

    @Operation(summary = "Secured endpoint for getting all animal orders as DTOs",
            description = "Returns a list of all animal orders with extended information." +
                    " This endpoint is secured and requires authentication with JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animal orders DTOs retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access."),
            @ApiResponse(responseCode = "403", description = "Forbidden access."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/secured/listdto")
    public List<AnimalOrderDTO> getAllAnimalOrdersDTOSecured() {
        return animalOrderService.getOrdersDTO();
    }


    //DTO STUFF END


    // Select animal orders by animal name
    @Operation(summary = "Endpoint for getting animal orders by animal ID",
            description = "Returns a list of animal orders for a specific animal ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animal orders retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Animal not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/byanimalid")
    public List<AnimalOrderEntity> getAnimalOrdersByAnimalID(
            @Parameter(description = "Animal ID", required = true)
            @RequestParam Long animalId) {
        //return animalOrderService.getAnimalOrdersByAnimal(animal);
        return animalOrderService.getAnimalOrdersByAnimalId(animalId);
    }

    // Select animal order by id
    @Operation(summary = "Endpoint for getting animal order by ID",
            description = "Returns an animal order for a specific ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal order retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Animal order not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/byid")
    public AnimalOrderEntity getAnimalOrderById(
            @Parameter(description = "Animal order ID", required = true)
            @RequestParam Long id) {
        return animalOrderService.getAnimalOrderById(id);
    }

    // Select all orders for a specific user
    @Operation(summary = "Endpoint for getting animal orders by user ID",
            description = "Returns a list of animal orders for a specific user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animal orders retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/byuserid")
    public List<AnimalOrderEntity> getAnimalOrdersByUserId(Long userId) {
        return animalOrderService.getAnimalOrdersByUserId(userId);
    }


    // Create a new animal order
    @Operation(summary = "Endpoint for creating a new animal order",
            description = "Creates a new animal order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Animal order created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/create")
    public ResponseEntity<AnimalOrderEntity> createAnimalOrder(
            @Parameter(description = "Details of the animal order", required = true)
            @RequestBody AnimalOrderEntity animalOrderEntity) {
        AnimalOrderEntity createdOrder = animalOrderService.createAnimalOrder(animalOrderEntity);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
//    public AnimalOrderEntity createAnimalOrder(
//            @Parameter(description = "Animal Order Entity", required = true)
//            @RequestBody AnimalOrderEntity animalOrderEntity) {
//        return animalOrderService.createAnimalOrder(animalOrderEntity);
//    }

    // Update animal order by id
    @Operation(summary = "Endpoint for updating an animal order",
            description = "Updates an existing animal order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal order updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided."),
            @ApiResponse(responseCode = "404", description = "Animal order not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<?> updateAnimalOrder(
            @Parameter(description = "Animal order ID as path", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated animal order details", required = true)
            @RequestBody AnimalOrderEntity updatedAnimalOrder) {
        try {
            animalOrderService.updateAnimalOrder(id, updatedAnimalOrder);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete animal order by id
    @Operation(summary = "Endpoint for deleting an animal order",
            description = "Deletes an existing animal order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal order deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Animal order not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteAnimalOrder(
            @Parameter(description = "Animal order ID as path", required = true)
            @PathVariable Long id) {
        animalOrderService.deleteAnimalOrder(id);
    }



}
