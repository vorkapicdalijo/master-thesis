package com.fer.hr.order.service.impl;

import com.fer.hr.clients.inventory.InventoryClient;
import com.fer.hr.clients.inventory.dto.InventoryItemRequest;
import com.fer.hr.clients.review.ReviewClient;
import com.fer.hr.clients.review.dto.Review;
import com.fer.hr.order.model.CartItem;
import com.fer.hr.order.model.Order;
import com.fer.hr.order.repository.OrderDao;
import com.fer.hr.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ReviewClient reviewClient;
    private final InventoryClient inventoryClient;

    @Override
    public void saveOrderDetails(Order order) {
        Long orderId = this.orderDao.insertOrderDetails(order);

        List<InventoryItemRequest> inventoryItems = new ArrayList<>();
        for (CartItem cartItem : order.getItems()) {
            cartItem.setOrderId(orderId);

            InventoryItemRequest inventoryItemRequest = InventoryItemRequest
                    .builder()
                    .productId(cartItem.getProductId())
                    .amount(cartItem.getAmount())
                    .build();
            inventoryItems.add(inventoryItemRequest);
        }
        //Update products amount after order in inventory
        inventoryClient.updateProductsAmountOnOrder(inventoryItems);


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
