package org.itechart.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter<T> {
    private ObjectMapper mapper = new ObjectMapper();

    public String toJson(T t){
        String json = null;
        try {
            json = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return json;
    }

    public T toObject(String jsonString, Class<T> clazz){
        T t = null;
        try {
            t = mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return t;
    }
}
