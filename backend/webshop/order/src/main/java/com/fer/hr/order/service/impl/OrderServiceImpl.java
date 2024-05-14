package com.fer.hr.order.service.impl;

import com.fer.hr.clients.review.ReviewClient;
import com.fer.hr.clients.review.dto.Review;
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
    private final ReviewClient reviewClient;

    @Override
    public void saveOrderDetails(Order order) {
        Long orderId = this.orderDao.insertOrderDetails(order);

        for (CartItem cartItem : order.getItems()) {
            cartItem.setOrderId(orderId);
        }

        this.orderDao.insertOrderItems(order.getItems());

    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = this.orderDao.getOrdersByUserId(userId);

        for (Order order : orders) {
            List<CartItem> cartItems = this.orderDao.getItemsByOrderId(order.getId());
            for (CartItem cartItem : cartItems) {
                Review review = reviewClient.getReviewByProductIdAndUserId(cartItem.getProductId(), userId).getBody();
                cartItem.setReview(review);
            }

            order.setItems(cartItems);
        }
        return orders;
    }
}
