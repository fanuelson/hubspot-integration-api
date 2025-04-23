package com.example.hubspotintegrationapi.gateways.inputs.http.validators;

import com.example.hubspotintegrationapi.gateways.inputs.http.validators.annotations.ContactEmailNonExistent;
import com.example.hubspotintegrationapi.usecases.contacts.validators.ContactEmailExistsValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactEmailExistsConstraintValidator
    implements ConstraintValidator<ContactEmailNonExistent, String> {

  private final ContactEmailExistsValidator contactEmailExistsValidator;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return contactEmailExistsValidator.validate(email).isEmpty();
  }
}
