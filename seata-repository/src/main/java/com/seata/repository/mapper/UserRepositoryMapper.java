package com.seata.repository.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seata.repository.mapper.entity.UserRepositoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepositoryMapper extends BaseMapper<UserRepositoryEntity> {
}
