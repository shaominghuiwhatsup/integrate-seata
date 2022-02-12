package com.seata.repository.controller;

import com.seata.repository.mapper.entity.UserOrderEntity;
import com.seata.repository.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:40
 */
@RestController
@RequestMapping("order")
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    @PostMapping("insert")
    public String insert() {
        UserOrderEntity entity = new UserOrderEntity();
//        entity.setId(1);
        entity.setOrderAmt(new BigDecimal(100));
        entity.setProductId(10);
        userOrderService.insert(entity);
        return "order ok";
    }
}
