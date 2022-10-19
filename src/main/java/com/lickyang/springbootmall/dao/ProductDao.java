package com.lickyang.springbootmall.dao;

import com.lickyang.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
