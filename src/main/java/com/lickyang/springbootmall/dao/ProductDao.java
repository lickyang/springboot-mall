package com.lickyang.springbootmall.dao;

import com.lickyang.springbootmall.constant.ProductCategory;
import com.lickyang.springbootmall.dto.ProductRequest;
import com.lickyang.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
