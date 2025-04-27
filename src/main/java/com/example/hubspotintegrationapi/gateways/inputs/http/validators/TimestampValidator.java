package com.example.hubspotintegrationapi.gateways.inputs.http.validators;

import static java.time.Instant.now;
import static java.time.Instant.ofEpochMilli;

import com.example.hubspotintegrationapi.domain.validation.ErrorMessage;
import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import com.example.hubspotintegrationapi.domain.validation.Validator;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import com.example.hubspotintegrationapi.gateways.inputs.http.validators.context.TimestampValidationContext;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TimestampValidator implements Validator<TimestampValidationContext> {

  public static final int MAX_EVENT_AGE_IN_MINUTES = 5;

  @Override
  public Optional<ValidationError> validate(@NonNull final TimestampValidationContext context) {
    val timestamp = context.timestamp();
    val durationInMinutes = ChronoUnit.MINUTES.between(ofEpochMilli(timestamp), now());

    if (Math.abs(durationInMinutes) > MAX_EVENT_AGE_IN_MINUTES) {
      return Optional.of(
          new ValidationError(ErrorMessage.INVALID_TIMESTAMP, timestamp.toString()));
    }

    return Optional.empty();
  }
}
