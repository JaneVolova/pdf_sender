package ru.blueteam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.blueteam.model.Note;

import java.util.List;

public class ConvertService {

    public static String convertListToJson(List<Note> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return json;
    }
}
