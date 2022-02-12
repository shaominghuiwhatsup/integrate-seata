package com.seata.integration.mapper.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.net.Inet4Address;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:11
 */
@TableName("user_integration")
@Data
public class UserIntegrationEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer point;
    private String userId;
}
