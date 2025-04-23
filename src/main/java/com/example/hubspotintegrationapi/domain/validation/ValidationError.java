package com.example.hubspotintegrationapi.domain.validation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
public class ValidationError implements Serializable {

  private final ErrorMessage errorMessage;
  private final List<? super Serializable> args;

  public ValidationError(@NonNull final ErrorMessage errorMessage, @Nullable Object... args) {
    this.errorMessage = errorMessage;
    this.args = List.of(Arrays.stream(ObjectUtils.defaultIfNull(args, new Object[] {})));
  }
}
