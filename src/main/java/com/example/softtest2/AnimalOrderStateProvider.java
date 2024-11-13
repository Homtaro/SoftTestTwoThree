package com.example.softtest2;

import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.state.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalOrderStateProvider {

    private final NewStateExecutor newStateExecutor;
    private final ProgressStateExecutor progressStateExecutor;
    private final CompletedStateExecutor completedStateExecutor;
    private final CancelledStateExecutor cancelledStateExecutor;



    public BaseStateExecutor getState(OrderStatus orderStatus){
        switch (orderStatus){
            case NEW:
                return newStateExecutor;
            case IN_PROGRESS:
                return progressStateExecutor;
            case COMPLETED:
                return completedStateExecutor;
            case CANCELLED:
                return cancelledStateExecutor;
            default:
                throw new IllegalArgumentException("Invalid order status");
        }

    }


}
