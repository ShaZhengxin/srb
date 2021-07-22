package com.szx.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description TODO
 * @author 沙政鑫
 * @date 2021/6/26  20:23
 */



// 使范围大些，可以访问其他微服务包
@SpringBootApplication
@ComponentScan({"com.szx.srb","com.szx.common"})
public class ServiceCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}
