package com.example.softtest2.service;

import com.example.softtest2.AnimalOrderStateProvider;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalOrderRepo;
import com.example.softtest2.state.BaseStateExecutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalOrderService {

    private final AnimalOrderRepo animalOrderRepo;
    private final AnimalOrderStateProvider animalOrderStateProvider;

//    @Autowired
//    public AnimalOrderService(AnimalOrderRepo animalOrderRepo) {
//        this.animalOrderRepo = animalOrderRepo;
//    }

    //Maybe there should be transactional, because its processing an order but not with
    // AnimalOrderProcessingService
    @Transactional
    public OrderStatus createQuickOrder(long animalId, int quantity) {

        AnimalOrderEntity animalOrder = new AnimalOrderEntity();
        animalOrder.setAnimalId(animalId);
        animalOrder.setQuantity(quantity);
        animalOrder.setStatus(OrderStatus.NEW);


        BaseStateExecutor executor = animalOrderStateProvider.getState(animalOrder.getStatus());
        executor.executeOrder(animalOrder);

        animalOrderRepo.save(animalOrder);


        return animalOrder.getStatus();

    }















    ///////////////////////////////////////////////////////////////

    // Select *
    public List<AnimalOrderEntity> getAllAnimalOrders() {
        return animalOrderRepo.findAll();
    }

    // Select with specific animal
    public List<AnimalOrderEntity> getAnimalOrdersByAnimalId(Long animalId) {
        //return animalOrderRepo.findByAnimal(animal);
        return animalOrderRepo.findByAnimalId(animalId);
    }

    // Select by ID
    public AnimalOrderEntity getAnimalOrderById(Long id) {
        return animalOrderRepo.findById(id).orElse(null);
    }

    // Create a new animal order

    public AnimalOrderEntity createAnimalOrder(AnimalOrderEntity animalOrderEntity) {
        return animalOrderRepo.save(animalOrderEntity);
    }

    // Update animal order by id
    @Transactional
    public void updateAnimalOrder(Long id, AnimalOrderEntity updatedAnimalOrder) {
        animalOrderRepo.findById(id)
                .ifPresent(animalOrder -> {
                    //animalOrder.setAnimalEntity(updatedAnimalOrder.getAnimalEntity());
                    //animalOrder.setAnimal(updatedAnimalOrder.getAnimal());
                    animalOrder.setAnimalId(updatedAnimalOrder.getAnimalId());
                    animalOrder.setQuantity(updatedAnimalOrder.getQuantity());
                    animalOrder.setStatus(updatedAnimalOrder.getStatus());
                    animalOrderRepo.save(animalOrder);
                });
    }

    // Delete animal order by id
    public void deleteAnimalOrder(Long id) {
        animalOrderRepo.deleteById(id);
    }





}
