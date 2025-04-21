package com.example.hubspotintegrationapi.service;

import com.example.hubspotintegrationapi.dto.CreateContactRequest;
import com.example.hubspotintegrationapi.dto.CreateContactResponse;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

  private static final String CLIENT_REGISTRATION_ID = "hubspot";
  private static final String HUBSPOT_BASE_URL = "https://api.hubapi.com";
  private static final String CONTACTS_RESOURCE = "/crm/v3/objects/contacts";

  private final RestClient restClient;

  public CreateContactResponse createContact(final CreateContactRequest createContact) {
    Map<String, Object> createContactBody =
        Map.of(
            "properties",
            Map.of(
                "email", UUID.randomUUID() + createContact.email(),
                "firstname", createContact.firstname(),
                "lastname", createContact.lastname()));
    return restClient
        .post()
        .uri(HUBSPOT_BASE_URL + CONTACTS_RESOURCE)
        .attributes(
            RequestAttributeClientRegistrationIdResolver.clientRegistrationId(
                CLIENT_REGISTRATION_ID))
        .body(createContactBody)
        .retrieve()
        .body(CreateContactResponse.class);
  }
}
