package com.lickyang.springbootmall.service;

import com.lickyang.springbootmall.dto.CreateOrderRequest;
import com.lickyang.springbootmall.dto.OrderQueryParams;
import com.lickyang.springbootmall.model.OrderSummary;

import java.util.List;

public interface OrderService {

    OrderSummary getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    List<OrderSummary> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
