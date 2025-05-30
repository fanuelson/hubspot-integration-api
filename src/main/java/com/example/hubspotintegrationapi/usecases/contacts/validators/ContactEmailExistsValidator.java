package com.example.hubspotintegrationapi.usecases.contacts.validators;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.example.hubspotintegrationapi.domain.validation.ErrorMessage;
import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import com.example.hubspotintegrationapi.domain.validation.Validator;
import com.example.hubspotintegrationapi.gateways.outputs.ContactsClientGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactEmailExistsValidator implements Validator<String> {

  private final ContactsClientGateway contactsClientGateway;

  @Override
  public Optional<ValidationError> validate(@NonNull final String contactEmail) {
    return contactsClientGateway
            .findByEmail(contactEmail)
            .map(this::mapToValidationError);
  }

  private ValidationError mapToValidationError(@NonNull final Contact contact) {
    return new ValidationError(ErrorMessage.CONTACT_EMAIL_ALREADY_EXISTS, contact.getEmail());
  }
}
