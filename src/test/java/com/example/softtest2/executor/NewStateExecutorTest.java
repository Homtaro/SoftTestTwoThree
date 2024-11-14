package com.example.softtest2.executor;

import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalRepo;
import com.example.softtest2.state.NewStateExecutor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class NewStateExecutorTest {

    @Mock
    private AnimalRepo animalRepo;

    @InjectMocks
    private NewStateExecutor newStateExecutor;


    @Test
    public void testStateExecutorReturnNew() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setQuantity(10);

        Mockito.when(animalRepo.findById(any())).thenReturn(Optional.of(animalEntity));

        AnimalOrderEntity animalOrderEntity = new AnimalOrderEntity();
        animalOrderEntity.setQuantity(1);

        OrderStatus orderStatus = newStateExecutor.executeOrder(animalOrderEntity);

        assertEquals(OrderStatus.IN_PROGRESS, orderStatus);
        assertEquals(OrderStatus.IN_PROGRESS, animalOrderEntity.getStatus());
        Mockito.verify(animalRepo, Mockito.times(1)).findById(any());

    }

    @Test
    public void testStateExecutorReturnCancelled() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setQuantity(10);

        Mockito.when(animalRepo.findById(any())).thenReturn(Optional.of(animalEntity));

        AnimalOrderEntity animalOrderEntity = new AnimalOrderEntity();
        animalOrderEntity.setQuantity(11);

        OrderStatus orderStatus = newStateExecutor.executeOrder(animalOrderEntity);

        assertEquals(OrderStatus.CANCELLED, orderStatus);
        assertEquals(OrderStatus.CANCELLED, animalOrderEntity.getStatus());
        Mockito.verify(animalRepo, Mockito.times(1)).findById(any());

    }

    @Test
    public void stateExecutorUpdatesAnimalQuantityWhenOrderIsProcessed() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setQuantity(10);

        Mockito.when(animalRepo.findById(any())).thenReturn(Optional.of(animalEntity));

        AnimalOrderEntity animalOrderEntity = new AnimalOrderEntity();
        animalOrderEntity.setQuantity(5);

        OrderStatus orderStatus = newStateExecutor.executeOrder(animalOrderEntity);

        assertEquals(OrderStatus.IN_PROGRESS, orderStatus);
        assertEquals(5, animalEntity.getQuantity());
        Mockito.verify(animalRepo, Mockito.times(1)).save(animalEntity);
    }

    @Test
    public void stateExecutorDoesNotUpdateAnimalQuantityWhenOrderIsCancelled() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setQuantity(10);

        Mockito.when(animalRepo.findById(any())).thenReturn(Optional.of(animalEntity));

        AnimalOrderEntity animalOrderEntity = new AnimalOrderEntity();
        animalOrderEntity.setQuantity(15);

        OrderStatus orderStatus = newStateExecutor.executeOrder(animalOrderEntity);

        assertEquals(OrderStatus.CANCELLED, orderStatus);
        assertEquals(10, animalEntity.getQuantity());
        Mockito.verify(animalRepo, Mockito.times(0)).save(animalEntity);
    }

    // Ideas for other tests:
    // 1. Add logic that returns animals to the warehouse when an order is cancelled from being processed
    // 2. Add logic to check for orders with quantity 0 or less




}
