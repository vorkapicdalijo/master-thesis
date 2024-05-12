package com.fer.hr.order.repository.impl;

import com.fer.hr.order.model.CartItem;
import com.fer.hr.order.model.Order;
import com.fer.hr.order.model.Person;
import com.fer.hr.order.repository.OrderDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("postgres_order")
@AllArgsConstructor
@Slf4j
public class OrderDaoImpl implements OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long insertOrderDetails(Order order) {
        final String sql =
                "INSERT INTO public.order (createdat, payid, payerid, sum, userid, firstname, lastname, email, phonenumber, address, city, zipcode, country) " +
                        "VALUES (:createdAt, :payId, :payerId, :sum, :userId, :firstName, :lastName, :email, :phoneNumber, :address, :city, :zipCode, :country)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("createdAt", order.getCreatedAt())
                .addValue("payId", order.getPayId())
                .addValue("payerId", order.getPayerId())
                .addValue("sum", order.getSum())
                .addValue("userId", order.getPerson().getUserId())
                .addValue("firstName", order.getPerson().getFirstName())
                .addValue("lastName", order.getPerson().getLastName())
                .addValue("email", order.getPerson().getEmail())
                .addValue("phoneNumber", order.getPerson().getPhoneNumber())
                .addValue("address", order.getPerson().getAddress())
                .addValue("city", order.getPerson().getCity())
                .addValue("zipCode", order.getPerson().getZipCode())
                .addValue("country", order.getPerson().getCountry());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[] { "id" });

            return keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            log.error(e.getMessage());

        }
        return 0L;
    }

    @Override
    public void insertOrderItems(List<CartItem> cartItems) {
        final String sql =
                "INSERT INTO public.cart_item (productid, name, price, amount, orderid) " +
                        "VALUES (:productId, :name, :price, :amount, :orderId)";

        for (CartItem cartItem : cartItems) {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("productId", cartItem.getProductId())
                    .addValue("name", cartItem.getName())
                    .addValue("price",cartItem.getPrice())
                    .addValue("amount", cartItem.getAmount())
                    .addValue("orderId", cartItem.getOrderId());

            try {
                namedParameterJdbcTemplate.update(sql, parameters);
            } catch (DataAccessException e) {
                log.error(e.getMessage());

            }
        }

    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        final String sql = "SELECT * FROM public.order WHERE userid = :userId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        try {
            return namedParameterJdbcTemplate.query(sql, params, orderRowMapper());

        } catch (DataAccessException e) {
            log.error(e.getMessage());

        }
        return null;
    }

    @Override
    public List<CartItem> getItemsByOrderId(Long orderId) {
        final String sql = "SELECT id, sum, payid, payerid, createdat FROM public.cart_item WHERE orderid = :orderId";

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        try {
            return namedParameterJdbcTemplate.query(sql, params, itemRowMapper());

        } catch (DataAccessException e) {
            log.error(e.getMessage());

        }
        return null;
    }

    public RowMapper<Order> orderRowMapper() {
        return ((rs, rowNum) -> {
           Order order = Order.builder()
                   .id(rs.getLong("id"))
                   .sum(rs.getFloat("sum"))
                   .payId(rs.getString("payid"))
                   .payerId(rs.getString("payerid"))
                   .createdAt(rs.getDate("createdat"))
                   .build();
           return order;
        });
    }

    public RowMapper<CartItem> itemRowMapper() {
        return ((rs, rowNum) -> {
            CartItem item = CartItem.builder()
                    .id(rs.getLong("id"))
                    .productId(rs.getLong("productid"))
                    .name(rs.getString("name"))
                    .price(rs.getDouble("price"))
                    .amount(rs.getInt("amount"))
                    .build();

            return item;
        });
    }
}
