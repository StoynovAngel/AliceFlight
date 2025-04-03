package com.pros.angel.flight.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            System.out.println("Cannot read json file. Message" + e.getMessage());
            return null;
        }
    }
}
