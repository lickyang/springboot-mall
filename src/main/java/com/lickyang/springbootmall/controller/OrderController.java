package com.lickyang.springbootmall.controller;

import com.lickyang.springbootmall.dto.CreateOrderRequest;
import com.lickyang.springbootmall.dto.OrderQueryParams;
import com.lickyang.springbootmall.model.OrderSummary;
import com.lickyang.springbootmall.service.OrderService;
import com.lickyang.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderSummary>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offSet
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offSet);

        //  取得 order list
        List<OrderSummary> orderSummaryList = orderService.getOrders(orderQueryParams);

        //  取得 order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        Page<OrderSummary> page = new Page<>();
        page.setTotal(count);
        page.setLimit(limit);
        page.setOffset(offSet);
        page.setResults(orderSummaryList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        OrderSummary order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
