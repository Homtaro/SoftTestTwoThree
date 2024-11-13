package com.example.softtest2.state;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Component
public class NewStateExecutor implements BaseStateExecutor{

    private final AnimalRepo animalRepo;


    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.NEW;
    }

    @Override
    public OrderStatus executeOrder(AnimalOrderEntity order) {
        animalRepo.findById(order.getAnimalId()).ifPresentOrElse(
                animal -> {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    if (animal.getQuantity() >= order.getQuantity()) {
                        animal.setQuantity(animal.getQuantity() - order.getQuantity());
                        animalRepo.save(animal);
                        order.setStatus(OrderStatus.IN_PROGRESS);
                    } else {
                        order.setStatus(OrderStatus.CANCELLED);
                    }
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found");
                }
        );
        return order.getStatus();
    }
}
