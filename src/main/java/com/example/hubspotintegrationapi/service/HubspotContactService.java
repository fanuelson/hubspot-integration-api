package com.example.hubspotintegrationapi.service;

import com.example.hubspotintegrationapi.dto.ContactRequest;
import com.example.hubspotintegrationapi.dto.ContactResponse;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class HubspotContactService {

  private final RestClient restClient;

  public ContactResponse createContact(ContactRequest request) {
    Map<String, Object> body =
        Map.of(
            "properties",
            Map.of(
                "email", UUID.randomUUID().toString() + request.email(),
                "firstname", request.firstname(),
                "lastname", request.lastname()));
    try {
      return restClient
          .post()
          .uri("https://api.hubapi.com/crm/v3/objects/contacts")
          .attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId("hubspot"))
          .body(body)
          .retrieve()
          .body(ContactResponse.class);
    } catch (HttpClientErrorException e) {
      // Log da exceção para análise
      System.err.println(
          "Erro ao criar contato: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
      throw e;
    }
  }
}
