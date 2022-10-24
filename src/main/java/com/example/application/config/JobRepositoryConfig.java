package com.example.application.config;

import com.example.application.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class JobRepositoryConfig {

    @Resource
    JobRepository jobRepository;

    @Bean
    public JobRepository jobRepository() {
        return jobRepository;
    }
}
