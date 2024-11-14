package com.example.softtest2.state;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class ProgressStateExecutor implements BaseStateExecutor{
    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.IN_PROGRESS;
    }

    @Override
    public OrderStatus executeOrder(AnimalOrderEntity order) {
        //Some logic

        order.setStatus(OrderStatus.COMPLETED);
        return order.getStatus();
    }
}
