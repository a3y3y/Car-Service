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

    public String toJson(Object value) {
        String json = null;
        try {
            json = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return json;
    }

    public <T> T toObject(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            t = mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return t;
    }
}
