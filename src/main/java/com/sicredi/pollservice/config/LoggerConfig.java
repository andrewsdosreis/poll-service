package com.sicredi.pollservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sicredi.pollservice.context.logger.LoggerInjector;

@Configuration
public class LoggerConfig {

    @Bean
    public LoggerInjector loggerInjector() {
        return new LoggerInjector();
    }
    
}