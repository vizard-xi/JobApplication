package com.example.application.controller;

import com.example.application.model.JobRequest;
import com.example.application.service.JobService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@CrossOrigin
@RestController
public class JobRestController {

    private final JobService jobService;

    public JobRestController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/createAndProcessNewJob")
    String createAndProcessedNewJob(@RequestBody JobRequest jobRequest) throws FileNotFoundException {

        if (jobRequest.providedCharacterValues() == null || jobRequest.providedCharacterValues().isEmpty()) {
            return "Provided Char Values is blank/null";
        }

        else if (jobRequest.numberOfStringsWanted() > Math.toIntExact
                (jobService.numberOfPossibleCharacterCombination
                        (jobRequest.providedCharacterValues().length()))) {
            return "Possible Combination of provided characters is less than what is specified";
        }
        else if (jobRequest.minimumStringLength() == 0) {
            return "Minimum Value cannot be Zero";
        }
        else if (jobRequest.maximumStringLength() < jobRequest.minimumStringLength()) {
            return "Maximum Value cannot be less than the minimum value";
        }
        return jobService.createAndProcessedNewJob(jobRequest);
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
