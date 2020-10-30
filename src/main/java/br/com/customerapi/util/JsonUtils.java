package br.com.customerapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.TimeZone;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public abstract class JsonUtils {

    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    /**
     * Configure the JSON Object mapper
     * @return the ObjectMapper
     */
    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setTimeZone(TimeZone.getDefault());
    }

    /**
     * Convert an Entity to a String JSON
     * @param a An Entity
     * @return The Entity string json
     * @throws JsonProcessingException throws the exception to be treated
     */
    public static String toJson(Object a) throws JsonProcessingException {
        return objectMapper.writeValueAsString(a);
    }

    /***
     * Convert an string json to a JsonNode
     * @param json the string json
     * @return JsonNode
     * @throws JsonProcessingException throws the exception to be treated
     */
    private static JsonNode parse(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }

    /***
     * Convert an String JSON to an Entity
     * @param json string json
     * @param clazz the Entity class
     * @param <A> the Entity type
     * @return The new Entity
     * @throws JsonProcessingException throws the exception to be treated
     */
    public static <A> A fromJson(String json, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(parse(json), clazz);
    }
}
