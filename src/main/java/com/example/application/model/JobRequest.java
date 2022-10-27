package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize()
public class JobRequest implements Job{

    @JsonProperty
    private int minimumStringLength;

    @JsonProperty
    private int maximumStringLength;

    @JsonProperty
    private String providedCharacterValues;

    @JsonProperty
    private int numberOfStringsWanted;

    public JobRequest(int minimumStringLength, int maximumStringLength, String providedCharacterValues, int numberOfStringsWanted) {
        this.minimumStringLength = minimumStringLength;
        this.maximumStringLength = maximumStringLength;
        this.providedCharacterValues = providedCharacterValues;
        this.numberOfStringsWanted = numberOfStringsWanted;
    }

    public JobRequest() {
    }

    public int minimumStringLength() {
        return minimumStringLength;
    }

    public int maximumStringLength() {
        return maximumStringLength;
    }

    public String providedCharacterValues() {
        return providedCharacterValues;
    }

    public int numberOfStringsWanted() {
        return numberOfStringsWanted;
    }

    @Override
    @JsonSetter
    public void setMinimumStringLength(int minimumStringLength) {
        this.minimumStringLength = minimumStringLength;
    }

    @Override
    @JsonSetter
    public void setMaximumStringLength(int maximumStringLength) {
        this.maximumStringLength = maximumStringLength;
    }

    @Override
    @JsonSetter
    public void setProvidedCharacterValues(String possibleCharacterValues) {
        this.providedCharacterValues = possibleCharacterValues;
    }

    @Override
    @JsonSetter
    public void setNumberOfStringsWanted(int numberOfStringsWanted) {
        this.numberOfStringsWanted = numberOfStringsWanted;
    }


}
