package com.seata.repository.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:37
 */
@Data
@TableName("user_order")
public class UserOrderEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer productId;
    private BigDecimal orderAmt;
}
