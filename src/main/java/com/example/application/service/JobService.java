package com.example.application.service;

import com.example.application.Utils.Queue.JobQueueReceiver;
import com.example.application.generator.JobGenerator;
import com.example.application.model.JobRequest;
import com.example.application.model.JobRow;
import com.example.application.model.JobState;
import com.example.application.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Scanner;

@Service
public class JobService {
    private final JobRepository jobRepository;

    private final JobGenerator jobGenerator;

    private final JobQueueReceiver jobQueueReceiver;

    public JobService(JobGenerator jobGenerator, JobRepository jobRepository, JobQueueReceiver jobQueueReceiver) {
        this.jobRepository = jobRepository;
        this.jobGenerator = jobGenerator;
        this.jobQueueReceiver = jobQueueReceiver;
    }

    public String createAndProcessedNewJob() {

        JobRequest jobRequestFromQueue = jobQueueReceiver.getJobRequestFromQueue();

        JobRow jobRow = new JobRow(JobState.CREATED.jobState(), jobRequestFromQueue.minimumStringLength(),
                jobRequestFromQueue.maximumStringLength(), jobRequestFromQueue.providedCharacterValues(), jobRequestFromQueue.numberOfStringsWanted());
        JobRow createdJob = jobRepository.save(jobRow);
        Optional <JobRow> retrievedJob = jobRepository.findById(createdJob.Id());
        if (retrievedJob.isPresent()) {
            processJob(retrievedJob.get());
            return "Job Created and Processed Successfully \n" + "Job {" +
                    "Job Id = " + retrievedJob.get().Id() +
                    ", Job State = '" + retrievedJob.get().jobState() +
                    '}' + '\n';
        }
        else {
            createdJob.setJobState(JobState.STOPPED.jobState());
            jobRepository.save(createdJob);
            return "Job with ID: " + createdJob.Id() + " created but could not be processed";
        }
    }
    private void processJob(JobRow job) {

        job.setJobState(JobState.PROCESSING.jobState());
        jobRepository.save(job);

        int minimumCombinationValues = job.minimumStringLength();
        int start = 1;
        String alphabet = job.providedCharacterValues();

        for (int characterIndex = 0; characterIndex != start; characterIndex ++) {

            String elements = alphabet.substring((start - 1), minimumCombinationValues);
            jobGenerator.generatePossibleCharacterCombination(elements);
            System.out.println();

            if (minimumCombinationValues < job.maximumStringLength()) {
                minimumCombinationValues++;
                start++;
            }
            else if (minimumCombinationValues == elements.length()) {
                break;
            }
            else if (minimumCombinationValues == alphabet.length()) {
                start = 1;
                if ((alphabet.length() - 1) == elements.length()) {
                    minimumCombinationValues  = alphabet.length();
                }
                else {
                    minimumCombinationValues = job.minimumStringLength() + 1;
                }

            }
        }

        job.setFileName(writeAndStoreProcessedJobResultsToAFile
                (jobGenerator.generatedCharacters().toString()));

        job.setJobState(JobState.COMPLETE.jobState());
        jobRepository.save(job);
    }

    public long numberOfPossibleCharacterCombination(long providedCharactersLength) {
        if(providedCharactersLength == 1 || providedCharactersLength == 0) return 1;

        return (providedCharactersLength) * numberOfPossibleCharacterCombination(providedCharactersLength-1);
    }

    private String writeAndStoreProcessedJobResultsToAFile(String generatedCharacters) {
        String fileName = Timestamp.from(Instant.now()) + ".txt";
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.println(generatedCharacters);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return fileName;
    }

    public int numberOfJobsRunning() {
        return jobRepository.findJobRowsByJobState(JobState.PROCESSING.jobState()).size();
    }

    public String jobResults(Long jobId) {
        Optional<JobRow> retrievedJob = jobRepository.findById(jobId);
        return retrievedJob.map(job -> readFileContent(Paths.get(job.fileName()).toFile()))
                .orElseGet(() -> "Job ID: " + jobId + "is not present");
    }

    private String readFileContent(File file) {

        StringBuilder fileContent = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String fileData = scanner.nextLine();
                fileContent.append(fileData).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return fileContent.toString();
    }

}
