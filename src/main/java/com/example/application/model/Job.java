package com.example.application.model;


public interface Job {

    int minimumStringLength();

    int maximumStringLength();

    String providedCharacterValues();

    int numberOfStringsWanted();

    void setMinimumStringLength(int minimumStringLength);

    void setMaximumStringLength(int maximumStringLength);

    void setProvidedCharacterValues(String possibleCharacterValues);

    void setNumberOfStringsWanted(int numberOfStringsWanted);


}
