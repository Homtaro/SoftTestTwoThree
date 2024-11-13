package com.example.softtest2.executor;


import com.example.softtest2.AnimalOrderStateProvider;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.state.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class AnimalOrderStateProviderTest {

    @Mock
    private NewStateExecutor newStateExecutor;

    @Mock
    private ProgressStateExecutor progressStateExecutor;

    @Mock
    private CompletedStateExecutor completedStateExecutor;

    @Mock
    private CancelledStateExecutor cancelledStateExecutor;

    @InjectMocks
    private AnimalOrderStateProvider animalOrderStateProvider;

    @Test
    public void testProviderReturnNew(){

//        Mockito.when(newStateExecutor.getOrderStatus()).thenReturn(OrderStatus.NEW);
//        BaseStateExecutor baseStateExecutor = animalOrderStateProvider.getState(OrderStatus.NEW);
//
//        assertEquals(newStateExecutor, baseStateExecutor);

        BaseStateExecutor baseStateExecutor = animalOrderStateProvider.getState(OrderStatus.NEW);
        assertThat(baseStateExecutor).isInstanceOf(NewStateExecutor.class);


    }

    @ParameterizedTest
    @MethodSource("getProviderModels")
    public void testProviderModelReturnNew(ProviderTestModel providerTestModel){

            BaseStateExecutor baseStateExecutor = animalOrderStateProvider.getState(providerTestModel.getOrderStatus());
            assertThat(baseStateExecutor).isInstanceOf(providerTestModel.getExpectedClass());

    }

    private static Stream<ProviderTestModel> getProviderModels(){
        return Stream.of(
                new ProviderTestModel(OrderStatus.NEW, NewStateExecutor.class),
                new ProviderTestModel(OrderStatus.IN_PROGRESS, ProgressStateExecutor.class),
                new ProviderTestModel(OrderStatus.COMPLETED, CompletedStateExecutor.class),
                new ProviderTestModel(OrderStatus.CANCELLED, CancelledStateExecutor.class)
        );
    }

}
