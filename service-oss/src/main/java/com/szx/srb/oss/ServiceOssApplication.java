package com.szx.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author szx
 * @description TODO
 * @date 2021/10/1  22:03
 */

@SpringBootApplication
@ComponentScan({"com.szx.srb","com.szx.common"})
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class,args);
    }
}
