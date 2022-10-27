package com.example.application.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueueUtil {

    public static <T> T convertToObject(String data, Class<T> classType){
        ObjectMapper objectMapper = new ObjectMapper();
        T queueObject = null;
        try {
            queueObject = objectMapper.readValue(data, classType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return queueObject;
    }

    public static <T> String convertToString(T string){
        ObjectMapper objectMapper = new ObjectMapper();
        String stringValues = null;

        try {
            stringValues = objectMapper.writeValueAsString(string);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringValues;
    }
}
