package com.fer.hr.order.service.impl;

import com.fer.hr.order.model.CartItem;
import com.fer.hr.order.model.Order;
import com.fer.hr.order.repository.OrderRepository;
import com.fer.hr.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void saveOrderDetails(Order order) {
        for (CartItem cartItem: order.getItems()) {
            cartItem.setOrder(order);
        }
        this.orderRepository.save(order);
    }
}
