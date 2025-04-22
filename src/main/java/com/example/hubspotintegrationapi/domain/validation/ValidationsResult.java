package com.example.hubspotintegrationapi.domain.validation;

import java.io.Serializable;
import java.util.*;
import org.springframework.lang.NonNull;

public class ValidationsResult implements Serializable {

  private final Set<ValidationError> errors;

  public ValidationsResult() {
    this.errors = new HashSet<>();
  }

  public void addError(@NonNull final ValidationError validationError) {
    this.errors.add(validationError);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }
}
