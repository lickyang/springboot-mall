package com.lickyang.springbootmall.service;

import com.lickyang.springbootmall.dto.CreateOrderRequest;
import com.lickyang.springbootmall.model.OrderSummary;

public interface OrderService {

    OrderSummary getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
