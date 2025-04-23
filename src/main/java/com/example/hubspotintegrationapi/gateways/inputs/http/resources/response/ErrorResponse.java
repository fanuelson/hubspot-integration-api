package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import java.util.List;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ErrorResponse {

  private final List<String> errors;

  public ErrorResponse(@NonNull final List<String> errors) {
    this.errors = errors;
  }

  public ErrorResponse(@NonNull final String error) {
    this.errors = List.of(error);
  }
}
