package com.example.hubspotintegrationapi.utils;

import com.example.hubspotintegrationapi.exceptions.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

  private static final String ERROR_TO_CONVERT_JSON_TO_OBJECT = "Error to convert Json to Object";
  private static final String ERROR_TO_CONVERT_OBJECT_TO_JSON = "Error to convert Object to Json";

  public static <T> Optional<T> toObject(
      @NonNull final String json, @NonNull final TypeReference<T> typeReference) {
    try {
      val objectMapper = getObjectMapper();
      T obj = objectMapper.readValue(json, typeReference);
      return Optional.of(obj);
    } catch (Exception ex) {
      throw new JsonParseException(ERROR_TO_CONVERT_JSON_TO_OBJECT, ex);
    }
  }

  public static <T> T toObject(@NonNull final String json, @NonNull final Class<T> clazz) {
    try {
      val objectMapper = getObjectMapper();
      return objectMapper.readValue(json, clazz);
    } catch (Exception ex) {
      throw new JsonParseException(ERROR_TO_CONVERT_JSON_TO_OBJECT, ex);
    }
  }

  public static String toJson(final Object object) {
    try {
      val objectMapper = getObjectMapper();
      return objectMapper.writeValueAsString(object);
    } catch (Exception ex) {
      throw new JsonParseException(ERROR_TO_CONVERT_OBJECT_TO_JSON, ex);
    }
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }
}
