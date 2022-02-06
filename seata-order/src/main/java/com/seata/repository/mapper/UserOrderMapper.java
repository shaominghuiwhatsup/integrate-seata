package com.seata.repository.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seata.repository.mapper.entity.UserOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrderEntity> {
}
