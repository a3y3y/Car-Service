package org.itechart.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class JsonConverter {
    private ObjectMapper mapper = new ObjectMapper();

    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);
    }

    public String toJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    public <T> T toObject(String jsonString, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonString, clazz);
    }
}
