package com.example.hubspotintegrationapi.gateways.inputs.http.validators;

import static com.example.hubspotintegrationapi.utils.HashUtils.sha256Hex;
import static org.apache.commons.lang3.BooleanUtils.negate;

import com.example.hubspotintegrationapi.domain.validation.ErrorMessage;
import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import com.example.hubspotintegrationapi.domain.validation.Validator;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.context.SignatureV1ValidationContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignatureValidatorV1 implements Validator<SignatureV1ValidationContext> {

  @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
  private String clientSecret;

  @Override
  public Optional<ValidationError> validate(@NonNull final SignatureV1ValidationContext context) {
    val sourceString = clientSecret + context.rawBody();
    val hashedString = sha256Hex(sourceString);
    val invalidSignature = negate(hashedString.equals(context.signature()));
    if (invalidSignature) {
      return Optional.of(new ValidationError(ErrorMessage.INVALID_V1_SIGNATURE));
    }
    return Optional.empty();
  }
}
