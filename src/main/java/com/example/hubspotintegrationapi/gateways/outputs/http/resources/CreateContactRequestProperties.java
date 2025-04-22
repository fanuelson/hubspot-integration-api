package com.example.hubspotintegrationapi.gateways.outputs.http.resources;

import com.example.hubspotintegrationapi.domain.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateContactRequestProperties {

  private final String email;
  @JsonProperty("firstname")
  private final String firstName;
  @JsonProperty("lastname")
  private final String lastName;

  public CreateContactRequestProperties(final Contact contact) {
    this.email = contact.getEmail();
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
  }
}
