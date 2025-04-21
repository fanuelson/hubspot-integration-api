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
public class HubspotContactService {

  private final RestClient restClient;

  public CreateContactResponse createContact(final CreateContactRequest createContact) {
    Map<String, Object> createContactBody =
        Map.of(
            "properties",
            Map.of(
                "email", UUID.randomUUID().toString() + createContact.email(),
                "firstname", createContact.firstname(),
                "lastname", createContact.lastname()));
    return restClient
        .post()
        .uri("https://api.hubapi.com/crm/v3/objects/contacts")
        .attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId("hubspot"))
        .body(createContactBody)
        .retrieve()
        .body(CreateContactResponse.class);
  }
}
