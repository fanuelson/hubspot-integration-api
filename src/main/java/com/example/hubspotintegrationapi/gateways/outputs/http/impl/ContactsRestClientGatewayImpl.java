package com.example.hubspotintegrationapi.gateways.outputs.http.impl;

import com.example.hubspotintegrationapi.domain.Contact;
import com.example.hubspotintegrationapi.domain.Properties;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClientGateway;
import com.example.hubspotintegrationapi.gateways.outputs.http.resources.CreateContactRequestProperties;
import com.example.hubspotintegrationapi.gateways.outputs.http.resources.CreateContactResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ContactsRestClientGatewayImpl implements ContactsRestClientGateway {

  private static final String CONTACTS_RESOURCE = "/crm/v3/objects/contacts";

  private final RestClient restClient;

  @Override
  public Optional<Contact> create(final Contact contact) {
    val properties = new CreateContactRequestProperties(contact);
    val response =
        restClient
            .post()
            .uri(CONTACTS_RESOURCE)
            .body(new Properties<>(properties))
            .retrieve()
            .body(CreateContactResponse.class);
    return Optional.ofNullable(response)
        .map(res -> contact.withId(res.getId()).withCreatedAt(res.getCreatedAt()));
  }
}
