/**
 * 
 */
package org.teapotech.blockly.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author jiangl
 *
 */
public class JsonHelper {

    private final SimpleModule module = new SimpleModule();

    ObjectMapper mapper = null;

    public <T> JsonHelper addDeserializer(Class<T> type, JsonDeserializer<T> deser) {
        module.addDeserializer(type, deser);
        return this;
    }

    public <T> JsonHelper addSerializer(Class<T> type, JsonSerializer<T> ser) {
        module.addSerializer(type, ser);
        return this;
    }

    private JsonHelper() {
        this.mapper = new ObjectMapper();
    }

    public JsonHelper(ObjectMapper mapper) {
        super();
        this.mapper = mapper;
    }

    public static JsonHelper newInstance() {
        return new JsonHelper();
    }

    public static JsonHelper newInstance(ObjectMapper mapper) {
        return new JsonHelper(mapper);
    }

    public JsonHelper build() {
        if (this.mapper == null) {
            mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(Include.NON_NULL).registerModule(module);

        } else {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(Include.NON_NULL).registerModule(module);
        }
        return this;
    }

    public String getJSON(Object o) throws JsonProcessingException {
        if (o == null) {
            return null;
        }
        String json = mapper.writeValueAsString(o);
        return json;
    }

    public String getPrettyJSON(Object o) throws JsonProcessingException {
        if (o == null) {
            return null;
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    public <T> T getObject(String json, Class<T> clazz) throws IOException {
        if (json == null) {
            return null;
        }
        return mapper.readValue(json, clazz);
    }

    public <T> T getObject(InputStream in, Class<T> clazz) throws IOException {
        if (in == null) {
            return null;
        }
        return mapper.readValue(in, clazz);
    }

    public <T> T getObject(JsonNode node, Class<T> clazz) {
        if (node == null) {
            return null;
        }
        return mapper.convertValue(node, clazz);
    }

    public <T> T getObject(JsonNode node, TypeReference<T> valueTypeRef) {
        if (node == null) {
            return null;
        }
        return mapper.convertValue(node, valueTypeRef);
    }

    public <T> T getObject(InputStream in, TypeReference<T> valueTypeRef) throws IOException {
        if (in == null) {
            return null;
        }
        return mapper.readValue(in, valueTypeRef);
    }

    public <T> T getObject(String json, TypeReference<T> valueTypeRef) throws IOException {
        if (json == null) {
            return null;
        }
        return mapper.readValue(json, valueTypeRef);
    }

    public JsonNode getObject(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        return mapper.readTree(in);
    }

    public JsonNode getObject(String jsonStr) throws IOException {
        if (jsonStr == null) {
            return null;
        }
        return mapper.readTree(jsonStr);
    }

    public JsonNode toJsonNode(Object pojo) {
        return mapper.convertValue(pojo, JsonNode.class);
    }

    public ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    public JsonNode replaceVariables(Object obj, Map<String, Object> variableValues) throws Exception {
        String json = getJSON(obj);
        if (json == null) {
            return null;
        }
        StringSubstitutor strSubstitutor = new StringSubstitutor(variableValues);
        json = strSubstitutor.replace(json);
        return getObject(json);
    }

    public <T> T mapToObject(Map<String, Object> map, Class<T> type) {
        return mapper.convertValue(map, type);
    }

}
