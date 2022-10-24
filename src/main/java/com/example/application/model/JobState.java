package com.example.application.model;

public enum JobState {
    CREATED("CREATED"),
    PROCESSING("PROCESSING"),
    STOPPED("STOPPED"),
    COMPLETE("COMPLETE");

    private final String jobState;

    JobState(String jobState) {
        this.jobState = jobState;
    }

    public String jobState() {
        return jobState;
    }
}
