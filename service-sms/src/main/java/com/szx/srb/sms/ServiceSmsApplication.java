package com.szx.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author szx
 * @description TODO
 * @date 2021/8/31  23:04
 */

@SpringBootApplication
@ComponentScan({"com.szx.srb","com.szx.common"})
public class ServiceSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class,args);
    }
}
