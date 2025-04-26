package com.example.softtest2.service;

import com.example.softtest2.AnimalOrderStateProvider;
import com.example.softtest2.dto.AnimalOrderDTO;
import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.entity.UserEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalOrderRepo;
import com.example.softtest2.repository.AnimalRepo;
import com.example.softtest2.repository.UserRepo;
import com.example.softtest2.state.BaseStateExecutor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalOrderService {

    private final AnimalOrderRepo animalOrderRepo;
    private final AnimalOrderStateProvider animalOrderStateProvider;

    //Stuff for DTO

    @Autowired
    private final AnimalRepo animalRepo;

    @Autowired
    private final UserRepo userRepo;


    //Temp function to get DTO going

//    public List<AnimalOrderDTO> getOrdersDTO() {
//
//        List<AnimalOrderEntity> orders = animalOrderRepo.findAll();
//
//        List<AnimalOrderDTO> dtos = new ArrayList<>();
//
//        for (AnimalOrderEntity order : orders) {
//
//            AnimalEntity animal = animalRepo.findById(order.getAnimalId()).orElseThrow(
//                    () -> new RuntimeException("Animal not found"));
//
//            UserEntity user = userRepo.findById(order.getUserId()).orElseThrow(
//                    () -> new RuntimeException("User not found"));
//
//            dtos.add(toDTO(order, animal, user));
//        }
//
//        return dtos;
//    }

    // Maybe non temp DTO function, hope it works

    @Transactional()
    public List<AnimalOrderDTO> getOrdersDTO() {
        try {
            List<AnimalOrderEntity> orders = animalOrderRepo.findAll();
            List<AnimalOrderDTO> dtos = new ArrayList<>(orders.size());

            Set<Long> animalIds = orders.stream()
                    .map(AnimalOrderEntity::getAnimalId)
                    .collect(Collectors.toSet());
            Set<Long> userIds = orders.stream()
                    .map(AnimalOrderEntity::getUserId)
                    .collect(Collectors.toSet());

            Map<Long, AnimalEntity> animals = animalRepo.findAllById(animalIds).stream()
                    .collect(Collectors.toMap(AnimalEntity::getId, a -> a));
            Map<Long, UserEntity> users = userRepo.findAllById(userIds).stream()
                    .collect(Collectors.toMap(UserEntity::getId, u -> u));

            for (AnimalOrderEntity order : orders) {
                AnimalEntity animal = animals.get(order.getAnimalId());
                UserEntity user = users.get(order.getUserId());

                if (animal == null) {
                    throw new EntityNotFoundException("Animal not found: " + order.getId());
                }
                if (user == null) {
                    throw new EntityNotFoundException("User not found: " + order.getId());
                }

                dtos.add(toDTO(order, animal, user));
            }
            return dtos;
        } catch (Exception e) {
            throw new ServiceException("Error fetching orders", e);
        }
    }




    public AnimalOrderDTO toDTO(AnimalOrderEntity animalOrderEntity, AnimalEntity animalEntity,
                                UserEntity userEntity) {

        AnimalOrderDTO animalOrderDTO = new AnimalOrderDTO();

        animalOrderDTO.setId(animalOrderEntity.getId());
        animalOrderDTO.setAnimalId(animalOrderEntity.getAnimalId());
        animalOrderDTO.setUserId(animalOrderEntity.getUserId());

        animalOrderDTO.setQuantity(animalOrderEntity.getQuantity());
        animalOrderDTO.setStatus(animalOrderEntity.getStatus().name());

        animalOrderDTO.setAnimalName(animalEntity.getAnimal());
        animalOrderDTO.setUserName(userEntity.getName()+" "+userEntity.getSurname());

        return animalOrderDTO;

    }





//    @Autowired
//    public AnimalOrderService(AnimalOrderRepo animalOrderRepo) {
//        this.animalOrderRepo = animalOrderRepo;
//    }

    //Maybe there should be transactional, because its processing an order but not with
    // AnimalOrderProcessingService
    @Transactional
    public OrderStatus createQuickOrder(long animalId, int quantity, long userId) {

        AnimalOrderEntity animalOrder = new AnimalOrderEntity();
        animalOrder.setAnimalId(animalId);
        animalOrder.setQuantity(quantity);
        animalOrder.setUserId(userId);
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


    // Select List by user id

    public List<AnimalOrderEntity> getAnimalOrdersByUserId(Long userId) {
        return animalOrderRepo.findByUserId(userId);
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
                    animalOrder.setUserId(updatedAnimalOrder.getUserId());
                    animalOrderRepo.save(animalOrder);
                });
    }

    // Delete animal order by id
    public void deleteAnimalOrder(Long id) {
        animalOrderRepo.deleteById(id);
    }





}
