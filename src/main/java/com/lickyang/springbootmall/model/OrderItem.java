package com.lickyang.springbootmall.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    //  訂單資訊
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;

    //  product 資訊
    private String productName;
    private String imageUrl;
}
