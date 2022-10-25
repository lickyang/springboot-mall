package com.lickyang.springbootmall.dao.impl;

import com.lickyang.springbootmall.dao.OrderDao;
import com.lickyang.springbootmall.dto.OrderQueryParams;
import com.lickyang.springbootmall.model.OrderItem;
import com.lickyang.springbootmall.model.OrderSummary;
import com.lickyang.springbootmall.rowmapper.OrderItemRowMapper;
import com.lickyang.springbootmall.rowmapper.OrderSummaryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public OrderSummary getOrderById(Integer orderId) {
        String sql =
                " SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                " FROM order_summary " +
                " WHERE 1 = 1 " +
                " AND order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderSummary> orderSummaryList = namedParameterJdbcTemplate.query(sql, map, new OrderSummaryRowMapper());

        if (orderSummaryList.size() == 1) {
            return orderSummaryList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql =
                " SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                " FROM order_item as oi " +
                " LEFT JOIN product as p on oi.product_id = p.product_id " +
                " WHERE 1 = 1 " +
                " AND oi.order_id = :orderId ";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql =
                " INSERT INTO order_summary " +
                " (user_id, total_amount, created_date, last_modified_date) " +
                " VALUES " +
                " (:userId, :totalAmount, :createdDate, :lastModifiedDate) ";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        //  使用 batchUpdate 提高效能
        String sql =
                " INSERT INTO order_item " +
                " (order_id, product_id, quantity, amount) " +
                " VALUES " +
                " (:orderId, :productId, :quantity, :amount) ";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i  = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public List<OrderSummary> getOrders(OrderQueryParams orderQueryParams) {
        String sql =
                " SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                " FROM order_summary " +
                " WHERE 1 = 1 ";

        Map<String, Object> map = new HashMap<>();

        //  查詢條件
        sql = addFilteringSql(sql, map, orderQueryParams);

        //  排序
        sql += " ORDER BY created_date DESC ";

        //  分頁
        sql += " LIMIT :limit OFFSET :offset ";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        System.out.println("sql : " + sql);

        List<OrderSummary> orderSummaryList = namedParameterJdbcTemplate.query(sql, map, new OrderSummaryRowMapper());
        return orderSummaryList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql =
                " SELECT count(*) " +
                " FROM order_summary " +
                " WHERE 1 = 1 ";

        Map<String, Object> map = new HashMap<>();

        //  查詢條件
        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }


    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        //  查詢條件
        if (orderQueryParams.getUserId() != null) {
            sql += " AND user_id = :userId ";
            map.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
