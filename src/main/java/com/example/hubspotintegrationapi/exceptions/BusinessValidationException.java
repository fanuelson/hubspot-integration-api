package com.example.hubspotintegrationapi.exceptions;

import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import java.util.List;

public class BusinessValidationException extends RuntimeException {

  private final List<ValidationError> validationErrors;

  public BusinessValidationException(final List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
  }

  public List<String> getErrors() {
    return this.validationErrors.stream().map(ValidationError::getError).toList();
  }
}
