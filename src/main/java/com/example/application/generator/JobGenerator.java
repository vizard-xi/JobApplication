package com.example.application.generator;

public class JobGenerator {

    private static StringBuilder generatedCharacters;

    public JobGenerator(StringBuilder generatedCharacters) {
        JobGenerator.generatedCharacters = generatedCharacters;
    }

    public void generatePossibleCharacterCombination(String stringValues) {
        generate("", stringValues);
    }

    private static void generate(String prefix, String stringValues) {
        int stringValueLength = stringValues.length();
        if (stringValueLength == 0) {
            generatedCharacters.append(prefix).append("\n");
            System.out.println(prefix);
        }
        else {
            for (int i = 0; i < stringValueLength; i++)
                generate(prefix + stringValues.charAt(i),
                        stringValues.substring(0, i) + stringValues.substring(i+1, stringValueLength));
        }
    }

    public StringBuilder generatedCharacters() {
        return generatedCharacters;
    }
}
