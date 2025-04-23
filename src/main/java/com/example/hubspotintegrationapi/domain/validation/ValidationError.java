package com.example.hubspotintegrationapi.domain.validation;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
public class ValidationError {

  private final String error;

  public ValidationError(@NonNull final ErrorMessage errorMessage, @Nullable Object... args) {
    this.error = errorMessage.getMessageTemplate().formatted(args);
  }
}
