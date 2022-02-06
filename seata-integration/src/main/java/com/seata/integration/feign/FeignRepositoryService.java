package com.seata.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "seata-repository", path = "repository")
public interface FeignRepositoryService {
    @PostMapping("insert")
    String insert();
}
