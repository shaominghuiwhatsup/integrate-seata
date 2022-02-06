package com.seata.integration.mapper.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.net.Inet4Address;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:11
 */
@TableName("user_integration")
@Data
public class UserIntegrationEntity {
    @TableId
    private Integer id;
    private Integer point;
    private String userId;
}
