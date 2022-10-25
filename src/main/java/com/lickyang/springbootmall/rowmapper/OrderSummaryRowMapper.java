package com.lickyang.springbootmall.rowmapper;

import com.lickyang.springbootmall.model.OrderSummary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderSummaryRowMapper implements RowMapper<OrderSummary> {
    @Override
    public OrderSummary mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderSummary orderSummary = new OrderSummary();

        orderSummary.setOrderId(resultSet.getInt("order_id"));
        orderSummary.setUserId(resultSet.getInt("user_id"));
        orderSummary.setTotalAmount(resultSet.getInt("total_amount"));
        orderSummary.setCreatedDate(resultSet.getTimestamp("created_date"));
        orderSummary.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return orderSummary;
    }
}
