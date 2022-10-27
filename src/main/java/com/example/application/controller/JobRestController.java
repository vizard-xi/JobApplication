package com.example.application.controller;

import com.example.application.Utils.Queue.JobQueuePublisher;
import com.example.application.model.JobRequest;
import com.example.application.service.JobService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class JobRestController {

    private final JobService jobService;

    private final JobQueuePublisher jobQueuePublisher;

    public JobRestController(JobService jobService, JobQueuePublisher jobQueuePublisher) {
        this.jobService = jobService;
        this.jobQueuePublisher = jobQueuePublisher;
    }

    @PostMapping("/createAndProcessNewJob")
    String createAndProcessedNewJob(@RequestBody List<JobRequest> jobRequest) {

        Map<Long, JobRequest> numberOfPossibleCharacterMap = new HashMap<>();

        if (jobRequest.size() > 0) {
           jobRequest.forEach(job -> numberOfPossibleCharacterMap
                   .put(jobService.numberOfPossibleCharacterCombination
                           (job.providedCharacterValues().length()), job));

           numberOfPossibleCharacterMap
                   .entrySet()
                   .stream()
                   .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                   .forEachOrdered(value -> numberOfPossibleCharacterMap.put(value.getKey(), value.getValue()));


            return numberOfPossibleCharacterMap.values().stream().map(job -> {
                if (job.providedCharacterValues() == null || job.providedCharacterValues().isEmpty()) {
                    return "Provided Char Values is blank/null";
                }

                else if (job.numberOfStringsWanted() > Math.toIntExact
                        (jobService.numberOfPossibleCharacterCombination
                                (job.providedCharacterValues().length()))) {
                    return "Possible Combination of provided characters is less than what is specified";
                }
                else if (job.minimumStringLength() == 0) {
                    return "Minimum Value cannot be Zero";
                }
                else if (job.maximumStringLength() < job.minimumStringLength()) {
                    return "Maximum Value cannot be less than the minimum value";
                }
                jobQueuePublisher.JobQueuePublisherThread(job);
                return jobService.createAndProcessedNewJob();
            }).collect(Collectors.joining());
        } else {
            return "Job Request List is empty";
        }
    }

    @GetMapping( "/numberOfJobRunning")
    int numberOfJobsRunning() {
        return jobService.numberOfJobsRunning();
    }

    @GetMapping("/jobResults/{jobId}")
    String jobResults(@PathVariable Long jobId) {
        return jobService.jobResults(jobId);
    }
}
