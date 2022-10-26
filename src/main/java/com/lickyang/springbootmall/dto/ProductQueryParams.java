package com.lickyang.springbootmall.dto;

import com.lickyang.springbootmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryParams {
    private ProductCategory category;
    private String search;
    private String orderBy = "created_date";
    private String sort = "desc";
    private Integer limit = 5;
    private Integer offset = 0;
}
