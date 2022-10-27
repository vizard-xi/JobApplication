package com.example.application.config;

import com.example.application.Utils.Queue.JobQueuePublisher;
import com.example.application.Utils.Queue.JobQueueReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JobQueueConfig {

    private final Jedis jedis = new Jedis();

    @Bean
    public JobQueueReceiver jobQueueReceiver() {
        return new JobQueueReceiver(jedis);
    }

    @Bean
    public JobQueuePublisher jobQueuePublisher() {
        return new JobQueuePublisher(jedis);
    }

}
