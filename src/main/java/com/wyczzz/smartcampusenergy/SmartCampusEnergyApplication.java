package com.wyczzz.smartcampusenergy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing // 开启 JPA 审计 (自动填充 createTime)
@EnableScheduling  // 开启定时任务
@EnableAsync       // 开启异步方法支持 (用于观察者模式)
@SpringBootApplication
public class SmartCampusEnergyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartCampusEnergyApplication.class, args);
    }
}