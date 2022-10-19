package com.lickyang.springbootmall.service.impl;

import com.lickyang.springbootmall.dao.ProductDao;
import com.lickyang.springbootmall.model.Product;
import com.lickyang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        Product product = productDao.getProductById(productId);
        return product;
    }
}
