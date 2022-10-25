package com.lickyang.springbootmall.dao;

import com.lickyang.springbootmall.dto.OrderQueryParams;
import com.lickyang.springbootmall.model.OrderItem;
import com.lickyang.springbootmall.model.OrderSummary;

import java.util.List;

public interface OrderDao {
    OrderSummary getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    List<OrderSummary> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
