package com.example.application.config;

import com.example.application.controller.JobRestController;
import com.example.application.generator.JobGenerator;
import com.example.application.repository.JobRepository;
import com.example.application.service.JobService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

@Configuration
@Import({JobRepositoryConfig.class})
public class JobConfig {

    @Resource
    private JobRepository jobRepository;

    @Bean
    public JobGenerator jobGenerator() {
        StringBuilder generatedCharacters = new StringBuilder();
        return new JobGenerator(generatedCharacters);
    }

    @Bean
    public JobRestController jobRestController() {
        return new JobRestController(new JobService(jobGenerator(), jobRepository));
    }
}
