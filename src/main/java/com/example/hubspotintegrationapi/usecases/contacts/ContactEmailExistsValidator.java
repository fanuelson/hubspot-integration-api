package com.example.hubspotintegrationapi.usecases.contacts;

import com.example.hubspotintegrationapi.domain.validation.ErrorMessage;
import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import com.example.hubspotintegrationapi.domain.validation.Validator;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClientGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactEmailExistsValidator implements Validator<String> {

  private final ContactsRestClientGateway contactsRestClientGateway;

  @Override
  public Optional<ValidationError> validate(@NonNull final String contactEmail) {
    return contactsRestClientGateway
        .findByEmail(contactEmail)
        .map(
            (contact ->
                new ValidationError(
                    ErrorMessage.CONTACT_EMAIL_ALREADY_EXISTS, contact.getEmail())));
  }
}
