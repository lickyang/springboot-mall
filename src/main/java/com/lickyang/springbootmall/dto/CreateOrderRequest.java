package com.lickyang.springbootmall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;
}
