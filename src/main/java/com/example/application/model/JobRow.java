package com.example.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JOB")
public class JobRow implements Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID", nullable = false)
    private Long Id;

    @Column(name = "JOB_STATE")
    private String jobState;

    @Column(name = "MINIMUM_STRING_LENGTH")
    private int minimumStringLength;

    @Column(name = "MAXIMUM_STRING_LENGTH")
    private int maximumStringLength;

    @Column(name = "PROVIDED_CHARACTER_VALUES")
    private String providedCharacterValues;

    @Column(name = "NUMBER_OF_STRINGS_WANTED")
    private int numberOfStringsWanted;

    @Column(name = "FILE_NAME")
    private String fileName;

    public JobRow(String jobState, int minimumStringLength, int maximumStringLength,
                  String providedCharacterValues, int numberOfStringsWanted) {
        this.jobState = jobState;
        this.minimumStringLength = minimumStringLength;
        this.maximumStringLength = maximumStringLength;
        this.providedCharacterValues = providedCharacterValues;
        this.numberOfStringsWanted = numberOfStringsWanted;
    }

    public JobRow() {

    }

    public Long Id() {
        return Id;
    }

    public String jobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

    public int minimumStringLength() {
        return minimumStringLength;
    }

    public void setMinimumStringLength(int minimumStringLength) {
        this.minimumStringLength = minimumStringLength;
    }

    public int maximumStringLength() {
        return maximumStringLength;
    }

    public void setMaximumStringLength(int maximumStringLength) {
        this.maximumStringLength = maximumStringLength;
    }

    @Override
    public void setProvidedCharacterValues(String possibleCharacterValues) {
        this.providedCharacterValues = possibleCharacterValues;
    }

    public String providedCharacterValues() {
        return providedCharacterValues;
    }


    public int numberOfStringsWanted() {
        return numberOfStringsWanted;
    }

    public void setNumberOfStringsWanted(int numberOfStringsWanted) {
        this.numberOfStringsWanted = numberOfStringsWanted;
    }

    public String fileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(Long id) {
        Id = id;
    }
}
