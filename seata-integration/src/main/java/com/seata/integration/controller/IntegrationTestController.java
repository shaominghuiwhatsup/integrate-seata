package com.seata.integration.controller;

import com.seata.integration.feign.FeignOrderService;
import com.seata.integration.feign.FeignRepositoryService;
import com.seata.integration.mapper.entity.UserIntegrationEntity;
import com.seata.integration.service.UserIntegrationServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shaominghui
 * @Date: 2022/1/30 6:31
 */
@RestController
@Slf4j
public class IntegrationTestController {
    @Autowired
    private UserIntegrationServiceImpl userIntegrationService;

    @Autowired
    private FeignOrderService feignOrderService;

    @Autowired
    private FeignRepositoryService feignRepositoryService;
//    @GetMapping
//    public String test() {
//        log.info("test");
//        return "ok~";
//    }

    @GlobalTransactional
    @PostMapping("insert")
    public String insert() {
        UserIntegrationEntity entity = new UserIntegrationEntity();
        entity.setId(1);
        entity.setPoint(1);
        entity.setUserId("555   55");
        userIntegrationService.insert(entity);

        feignOrderService.insert();
        feignRepositoryService.insert();
        int i = 1/0;
        return "ok";
    }

}
