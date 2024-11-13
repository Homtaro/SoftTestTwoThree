package com.example.softtest2.service;


import com.example.softtest2.AnimalOrderStateProvider;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalOrderRepo;
import com.example.softtest2.state.BaseStateExecutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnimalOrderProcessingService {

    private final AnimalOrderRepo animalOrderRepo;
    private final AnimalOrderStateProvider animalOrderStateProvider;

    @Transactional
    public OrderStatus processOrder(long orderId) {

        return animalOrderRepo.findById(orderId).map(
                animalOrderEntity -> {
                    BaseStateExecutor stateExecutor = animalOrderStateProvider.getState(animalOrderEntity.getStatus());
                    return stateExecutor.executeOrder(animalOrderEntity);
                }
        ).orElseThrow( () -> new RuntimeException("Order not found" + orderId) );
    }


}
