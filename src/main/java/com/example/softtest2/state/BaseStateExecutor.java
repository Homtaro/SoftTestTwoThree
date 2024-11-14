package com.example.softtest2.state;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;

public interface BaseStateExecutor {

    OrderStatus getOrderStatus();

    OrderStatus executeOrder(AnimalOrderEntity order);



}
