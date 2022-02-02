package com.seata.integration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shaominghui
 * @Date: 2022/1/30 6:31
 */
@RestController
@Slf4j
public class IntegrationTestController {
    @GetMapping
    public String test() {
        log.info("test");
        return "ok~";
    }

}
