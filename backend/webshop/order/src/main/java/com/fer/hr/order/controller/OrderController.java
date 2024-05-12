package com.fer.hr.order.controller;

import com.fer.hr.order.model.Order;
import com.fer.hr.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<Void> sendOrderDetails(@RequestBody Order order) {
        orderService.saveOrderDetails(order);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
