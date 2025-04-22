package com.example.hubspotintegrationapi.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

  public static <T> Optional<T> toObject(String json, final TypeReference<T> typeReference) {
    try {
      val objectMapper = getObjectMapper();
      T obj = objectMapper.readValue(json, typeReference);
      return Optional.of(obj);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }
}
