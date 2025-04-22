package com.example.hubspotintegrationapi.gateways.outputs.http.impl;

import com.example.hubspotintegrationapi.domain.CreateContactRequest;
import com.example.hubspotintegrationapi.domain.CreateContactResponse;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClient;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ContactsRestClientImpl
    implements ContactsRestClient<CreateContactRequest, CreateContactResponse> {

  private static final String CONTACTS_RESOURCE = "/crm/v3/objects/contacts";

  private final RestClient restClient;

  @Override
  public CreateContactResponse create(CreateContactRequest createContactRequest) {
    Map<String, Object> createContactBody =
        Map.of(
            "properties",
            Map.of(
                "email", UUID.randomUUID() + createContactRequest.email(),
                "firstname", createContactRequest.firstname(),
                "lastname", createContactRequest.lastname()));
    return restClient
        .post()
        .uri(CONTACTS_RESOURCE)
        .body(createContactBody)
        .retrieve()
        .body(CreateContactResponse.class);
  }
}
