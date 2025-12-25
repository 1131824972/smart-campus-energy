package com.wyczzz.smartcampusenergy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi; // 注意包名是 models (Spring Boot 3 特征)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // 配置基本信息
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧校园能耗管理平台 API")
                        .version("1.0")
                        .description("基于 Spring Boot 3 + Vue 3 的期末项目接口文档"));
    }

    // 配置分组（关键！解决请求异常的核心）
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("default") // 分组名称
                .pathsToMatch("/**") // 匹配所有路径
                .packagesToScan("com.wyczzz.smartcampusenergy.controller") // 扫描你的 Controller 包
                .build();
    }
}