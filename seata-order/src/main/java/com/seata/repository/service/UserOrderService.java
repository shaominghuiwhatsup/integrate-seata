package com.seata.repository.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seata.repository.mapper.UserOrderMapper;
import com.seata.repository.mapper.entity.UserOrderEntity;
import org.springframework.stereotype.Service;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:39
 */
@Service
public class UserOrderService extends ServiceImpl<UserOrderMapper, UserOrderEntity> {
}
