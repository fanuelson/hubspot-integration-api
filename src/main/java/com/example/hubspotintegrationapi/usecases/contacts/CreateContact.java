package com.example.hubspotintegrationapi.usecases.contacts;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.example.hubspotintegrationapi.exceptions.BusinessValidationException;
import com.example.hubspotintegrationapi.gateways.outputs.ContactsRestClientGateway;
import com.example.hubspotintegrationapi.usecases.contacts.validators.ContactEmailExistsValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateContact {

  private final ContactsRestClientGateway contactsRestClientGateway;
  private final ContactEmailExistsValidator contactEmailExistsValidator;

  public Contact execute(@NonNull final Contact contact) {
      try {
          contactEmailExistsValidator
                  .validate(contact.getEmail())
                  .ifPresent(validationError -> {
                      throw new BusinessValidationException(List.of(validationError));
                  });
          return contactsRestClientGateway.create(contact).orElseThrow();
      } catch (Exception ex) {
          log.info("need to save contact to process later");
          throw ex;
      }

  }
}
