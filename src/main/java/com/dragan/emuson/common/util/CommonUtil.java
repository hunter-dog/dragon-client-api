package com.dragan.emuson.common.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommonUtil {

    /**
     * @param JsonObject
     * @apiNote JSONObject를 Map<String, String> 형식으로 변환처리.
     * @return Map<String,String>
     * **/
    public static Map<String, Object> getMapFromJsonObject(JsonObject jsonObj){
        Map map = null;

        try {
            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
        } catch (JsonParseException | IOException e) {
            log.error("error {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return map;
    }

    public static JsonArray convertLongListToJsonArray(List<Long> list) {
        JsonArray jsonArray = new JsonArray();

        for (Long item : list) {
            jsonArray.add(item);
        }

        return jsonArray;
    }



}
