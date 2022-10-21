package com.lickyang.springbootmall.service;

import com.lickyang.springbootmall.dto.ProductRequest;
import com.lickyang.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
