package com.wyczzz.smartcampusenergy.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 标记这是一个配置类
public class ModelMapperConfig {

    @Bean // 注册一个 Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // 设置为“严格匹配模式”
        // 防止比如 User 里的 password 字段不小心映射到了 DTO 里的某个无关字段
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}