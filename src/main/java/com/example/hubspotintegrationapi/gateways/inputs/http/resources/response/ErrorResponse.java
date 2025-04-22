package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ErrorResponse implements Serializable {

  private final Set<String> errors;

  public ErrorResponse(@NonNull final Set<String> errors) {
    this.errors = errors;
  }

  public ErrorResponse(@NonNull final String error) {
    this.errors = Set.of(error);
  }
}
