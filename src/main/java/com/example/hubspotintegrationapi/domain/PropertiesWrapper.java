package com.example.hubspotintegrationapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesWrapper<T> {
  private T properties;

  public static <T> PropertiesWrapper<T> create(T prop) {
    return new PropertiesWrapper<>(prop);
  }
}
