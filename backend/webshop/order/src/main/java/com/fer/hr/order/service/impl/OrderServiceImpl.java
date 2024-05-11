package com.fer.hr.order.service.impl;

import com.fer.hr.order.model.CartItem;
import com.fer.hr.order.model.Order;
import com.fer.hr.order.repository.OrderDao;
import com.fer.hr.order.repository.OrderRepository;
import com.fer.hr.order.repository.impl.OrderDaoImpl;
import com.fer.hr.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Override
    public void saveOrderDetails(Order order) {
        Long orderId = this.orderDao.insertOrderDetails(order);

        for (CartItem cartItem : order.getItems()) {
            cartItem.setOrderId(orderId);
        }

        this.orderDao.insertOrderItems(order.getItems());

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = this.orderDao.getOrdersByUserId(userId);

        for (Order order : orders) {
            List<CartItem> cartItems = this.orderDao.getItemsByOrderId(order.getId());

            order.setItems(cartItems);
        }
        return orders;
    }
}
