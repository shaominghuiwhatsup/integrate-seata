package com.seata.integration.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seata.integration.mapper.UserIntegrationMapper;
import com.seata.integration.mapper.entity.UserIntegrationEntity;
import org.springframework.stereotype.Service;

@Service
public class UserIntegrationServiceImpl extends ServiceImpl<UserIntegrationMapper, UserIntegrationEntity> {
}
