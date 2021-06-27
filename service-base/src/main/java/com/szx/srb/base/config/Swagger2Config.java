package com.szx.srb.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description TODO
 * @Author 沙政鑫
 * @Data 2021/6/26  23:33
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
/*
    为了将后台管理系统和前端用户接口分开，就需要配置两个bean
 */
    //Docket swagger文档对象
    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("尚融宝后台管理系统API文档")
                .description("本文档描述了尚融宝后台管理系统的各个模块的接口的调用方式")
                .version("1.0")
                .contact(new Contact("szx","https://www.baidu.com/","12032323@qq.com"))
                .build();
    }

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("尚融宝网站API文档")
                .description("本文档描述了尚融宝网站的各个模块的接口的调用方式")
                .version("1.0")
                .contact(new Contact("szx","https://www.baidu.com/","12032323@qq.com"))
                .build();
    }
}
