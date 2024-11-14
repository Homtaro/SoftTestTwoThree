package com.example.softtest2.state;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class CancelledStateExecutor implements BaseStateExecutor{
    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.CANCELLED;
    }

    @Override
    public OrderStatus executeOrder(AnimalOrderEntity order) {
        throw new RuntimeException("Order is already cancelled");
    }
}
