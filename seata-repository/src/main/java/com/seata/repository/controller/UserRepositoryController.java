package com.seata.repository.controller;

import com.seata.repository.mapper.entity.UserRepositoryEntity;
import com.seata.repository.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shaominghui
 * @Date: 2022/2/6 23:49
 */
@RestController
@RequestMapping("repository")
public class UserRepositoryController {
    @Autowired
    private UserRepositoryService userRepositoryService;

    @PostMapping("insert")
    public String insert() {

        UserRepositoryEntity entity = new UserRepositoryEntity();
//        entity.setId(1);
        entity.setNum(4);
        entity.setProductId(190);
        userRepositoryService.insert(entity);
        return "repository ok";
    }
}
