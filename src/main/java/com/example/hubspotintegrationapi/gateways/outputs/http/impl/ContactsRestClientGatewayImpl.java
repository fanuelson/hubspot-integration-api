package com.example.hubspotintegrationapi.gateways.outputs.http.impl;

import com.example.hubspotintegrationapi.config.restclient.HubspotRestClientProp;
import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.example.hubspotintegrationapi.domain.wrappers.PropertiesWrapper;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClientGateway;
import com.example.hubspotintegrationapi.gateways.outputs.http.resources.CreateContactRequestProperties;
import com.example.hubspotintegrationapi.gateways.outputs.http.resources.CreateContactResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactsRestClientGatewayImpl implements ContactsRestClientGateway {

  private final HubspotRestClientProp hubspotRestClientProp;

  private final RestClient restClient;

  @Override
  public Optional<Contact> create(@NonNull final Contact contact) {
    val properties = new CreateContactRequestProperties(contact);
    val response =
        restClient
            .post()
            .uri(hubspotRestClientProp.getContactsResource())
            .body(PropertiesWrapper.create(properties))
            .retrieve()
            .body(CreateContactResponse.class);

    return Optional.ofNullable(response).map(res -> res.toDomain(contact));
  }

  @Override
  public Optional<Contact> findByEmail(String email) {
    // TODO: Buscar contato no hubspot pelo email
    return Optional.empty();
  }
}
