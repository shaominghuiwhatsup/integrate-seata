package com.seata.repository.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.omg.CORBA.INTERNAL;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:45
 */
@Data
@TableName("user_repository")
public class UserRepositoryEntity {
    @TableId
    private Integer id;

    private Integer productId;

    private Integer num;
}
