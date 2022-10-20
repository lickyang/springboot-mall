package com.lickyang.springbootmall.dao;

import com.lickyang.springbootmall.dto.ProductRequest;
import com.lickyang.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
