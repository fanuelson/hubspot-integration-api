package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

@Getter
public class ErrorResponse implements Serializable {

  private final Set<String> errors;

  public ErrorResponse(final Set<String> errors) {
    this.errors = ObjectUtils.defaultIfNull(errors, Set.of());
  }

  public ErrorResponse(final String error) {
    this.errors = Optional.of(error).map(Set::of).orElse(Set.of());
  }
}
