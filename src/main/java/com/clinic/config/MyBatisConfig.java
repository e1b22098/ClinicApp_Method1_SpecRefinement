package com.clinic.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.clinic.repository")
public class MyBatisConfig {
}

