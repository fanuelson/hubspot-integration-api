package com.example.hubspotintegrationapi.domain.validation;

import java.io.Serializable;
import java.util.*;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ValidationsResult implements Serializable {

  private final List<ValidationError> errors;

  public ValidationsResult() {
    this.errors = new ArrayList<>();
  }

  public void addError(@NonNull final ValidationError validationError) {
    this.errors.add(validationError);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }
}
