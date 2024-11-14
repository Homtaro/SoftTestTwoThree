package com.example.softtest2.executor;

import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.state.BaseStateExecutor;



public class ProviderTestModel {

    private OrderStatus orderStatus;
    private Class<? extends BaseStateExecutor> expectedClass;


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Class<? extends BaseStateExecutor> getExpectedClass() {
        return expectedClass;
    }

    public void setExpectedClass(Class<? extends BaseStateExecutor> expectedClass) {
        this.expectedClass = expectedClass;
    }

    public ProviderTestModel(OrderStatus orderStatus, Class<? extends BaseStateExecutor> expectedClass) {
        this.orderStatus = orderStatus;
        this.expectedClass = expectedClass;
    }

}
