package com.example.hubspotintegrationapi.usecases.contacts;

import com.example.hubspotintegrationapi.domain.Contact;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClientGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateContact {

  private final ContactsRestClientGateway contactsRestClientGateway;

  public Contact execute(final Contact contact) {
    return contactsRestClientGateway.create(contact).orElseThrow();
  }
}
