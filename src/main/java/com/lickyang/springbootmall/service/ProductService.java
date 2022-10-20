package com.lickyang.springbootmall.service;

import com.lickyang.springbootmall.dto.ProductRequest;
import com.lickyang.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
