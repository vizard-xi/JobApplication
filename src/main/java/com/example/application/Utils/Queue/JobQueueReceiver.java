package com.example.application.Utils.Queue;

import com.example.application.Utils.QueueUtil;
import com.example.application.model.JobRequest;
import redis.clients.jedis.Jedis;

public class JobQueueReceiver  {

    private final Jedis jedis;

    public JobQueueReceiver(Jedis jedis) {
        this.jedis = jedis;
    }

    public JobRequest getJobRequestFromQueue() {
        return QueueUtil.convertToObject(jedis.get("Job Request"), JobRequest.class);
    }
}
