package com.example.softtest2.service;

import com.example.softtest2.AnimalOrderStateProvider;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalOrderRepo;
import com.example.softtest2.state.NewStateExecutor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnimalOrderServiceTest {


    @Mock
    private AnimalOrderRepo animalOrderRepo;

    @Mock
    private AnimalOrderStateProvider animalOrderStateProvider;

    @Mock
    private NewStateExecutor newStateExecutor;



    @InjectMocks
    AnimalOrderService animalOrderService;


    @Test
    public void testCreateQuickOrderMustReturnNew() {
        /*when(animalOrderRepo.save(any())).thenReturn(new AnimalOrderEntity());
        OrderStatus orderStatus = animalOrderService.createQuickOrder(1, 1);
        Mockito.verify(animalOrderRepo, Mockito.times(1)).save(any(AnimalOrderEntity.class));
        assertEquals(OrderStatus.NEW, orderStatus);*/


        AnimalOrderEntity animalOrderEntity = new AnimalOrderEntity();

        when(animalOrderRepo.save(any())).thenReturn(animalOrderEntity);
        when(animalOrderStateProvider.getState(OrderStatus.NEW)).thenReturn(newStateExecutor);
        when(newStateExecutor.executeOrder(animalOrderEntity)).thenReturn(OrderStatus.IN_PROGRESS);
        when(newStateExecutor.getOrderStatus()).thenReturn(OrderStatus.NEW);



        OrderStatus orderStatus = animalOrderService.createQuickOrder(1, 1);
        Mockito.verify(animalOrderRepo, Mockito.times(1)).save(any(AnimalOrderEntity.class));
        assertEquals(OrderStatus.NEW, orderStatus);



    }

}
