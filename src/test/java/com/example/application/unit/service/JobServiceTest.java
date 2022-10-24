package com.example.application.unit.service;

import com.example.application.generator.JobGenerator;
import com.example.application.model.JobRequest;
import com.example.application.model.JobRow;
import com.example.application.model.JobState;
import com.example.application.repository.JobRepository;
import com.example.application.service.JobService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JobServiceTest {

    private JobRepository jobRepository;

    private JobGenerator jobGenerator;

    private JobService jobService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jobRepository = Mockito.mock(JobRepository.class);
        jobGenerator = Mockito.spy(new JobGenerator(new StringBuilder()));
        jobService = Mockito.spy(new JobService(jobGenerator, jobRepository));
    }

    @AfterEach
    public void tearDown() {
        jobGenerator = null;
        jobRepository = null;
        jobService = null;
    }

    @Test
    public void testCreateAndProcessedNewJob() throws FileNotFoundException {
        String expected = "Job Created and Processed Successfully \n" + "Job {" +
                "Job Id = " + 1 +
                ", Job State = '" + JobState.COMPLETE.jobState() +
                '}';

        JobRequest jobRequest
                = new JobRequest(2, 4,"abcd", 10);

        JobRow jobRow = new JobRow(JobState.CREATED.jobState(), 2,
                4, "abcd", 10);

        jobRow.setId(1L);

        when(jobRepository.save(ArgumentMatchers.any())).thenReturn(jobRow);
        when(jobRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(jobRow));

        String actual = jobService.createAndProcessedNewJob(jobRequest);

        MatcherAssert.assertThat(jobRow.jobState(), is(JobState.COMPLETE.jobState()));
        MatcherAssert.assertThat(expected, is(actual));
    }

    @Test
    public void testCreateAndProcessedNewJobWhenRetrievedJobIsEmpty() throws FileNotFoundException {

        String expected = "Job with ID: 1 created but could not be processed";

        JobRequest jobRequest
                = new JobRequest(2, 4,"abcd", 10);

        JobRow jobRow = new JobRow(JobState.CREATED.jobState(), 2,
                4, "abcd", 10);

        jobRow.setId(1L);

        when(jobRepository.save(ArgumentMatchers.any())).thenReturn(jobRow);
        when(jobRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        String actual = jobService.createAndProcessedNewJob(jobRequest);

        MatcherAssert.assertThat(jobRow.jobState(), is(JobState.STOPPED.jobState()));
        MatcherAssert.assertThat(expected, is(actual));


    }
}
