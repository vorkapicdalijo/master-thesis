package com.fer.hr.order.repository;

import com.fer.hr.order.model.CartItem;
import com.fer.hr.order.model.Order;

import java.util.List;

public interface OrderDao {

    public Long insertOrderDetails(Order order);

    public void insertOrderItems(List<CartItem> cartItems);

    public List<Order> getOrdersByUserId(String userId);

    public List<CartItem> getItemsByOrderId(Long orderId);
}
