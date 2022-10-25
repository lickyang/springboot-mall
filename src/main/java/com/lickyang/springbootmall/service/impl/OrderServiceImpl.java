package com.lickyang.springbootmall.service.impl;

import com.lickyang.springbootmall.dao.OrderDao;
import com.lickyang.springbootmall.dao.ProductDao;
import com.lickyang.springbootmall.dao.UserDao;
import com.lickyang.springbootmall.dto.BuyItem;
import com.lickyang.springbootmall.dto.CreateOrderRequest;
import com.lickyang.springbootmall.dto.OrderQueryParams;
import com.lickyang.springbootmall.model.OrderItem;
import com.lickyang.springbootmall.model.OrderSummary;
import com.lickyang.springbootmall.model.Product;
import com.lickyang.springbootmall.model.User;
import com.lickyang.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public OrderSummary getOrderById(Integer orderId) {
        OrderSummary orderSummary = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        orderSummary.setOrderItemList(orderItemList);

        return orderSummary;
    }

    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //  檢查 user 是否存在
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            //  檢查 product 是否存在，庫存是否充足
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩下庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //  扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());


            //  計算總價值
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            //  轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public List<OrderSummary> getOrders(OrderQueryParams orderQueryParams) {
        return orderDao.getOrders(orderQueryParams);
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
