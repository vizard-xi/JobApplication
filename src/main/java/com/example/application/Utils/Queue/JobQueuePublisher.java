package com.example.application.Utils.Queue;


import com.example.application.Utils.QueueUtil;
import com.example.application.model.JobRequest;
import redis.clients.jedis.Jedis;

public class JobQueuePublisher {

    private final Jedis jedis;

    public JobQueuePublisher(Jedis jedis) {
        this.jedis = jedis;
    }

    public void JobQueuePublisherThread(JobRequest jobRequest) {
        jedis.set("Job Request", QueueUtil.convertToString(jobRequest));
    }

}
