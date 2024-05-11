package com.fer.hr.order.service;

import com.fer.hr.order.model.Order;

import java.util.List;

public interface OrderService {
    public void saveOrderDetails(Order order);

    public List<Order> getOrdersByUserId(Long userId);
}
