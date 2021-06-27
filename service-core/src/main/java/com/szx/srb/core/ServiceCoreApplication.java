package com.szx.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description TODO
 * @Author 沙政鑫
 * @Data 2021/6/26  20:23
 */

@SpringBootApplication
@ComponentScan({"com.szx.srb","com.szx.common"})//使范围大些，可以访问其他微服务包
public class ServiceCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}
