package com.fer.hr.order.repository;

import com.fer.hr.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_order")
@AllArgsConstructor
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    public void insertOrderDetails(Order order) {
        final String sql = "INSERT"
    }
}
