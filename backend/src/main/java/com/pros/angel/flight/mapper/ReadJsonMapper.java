package com.pros.angel.flight.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pros.angel.flight.exception.JsonMappingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadJsonMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJSONFile(String filename, Class<T> jsonClass) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException("File does not exists");
            }
            return objectMapper.readValue(file, jsonClass);
        } catch (IOException e) {
            String errorMessage = "Cannot read json file. Message: " + e.getMessage();
            System.out.println(errorMessage);
            throw new JsonMappingException(errorMessage);
        }
    }
}
