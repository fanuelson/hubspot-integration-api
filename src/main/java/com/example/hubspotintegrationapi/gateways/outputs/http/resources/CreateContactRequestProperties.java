package com.example.hubspotintegrationapi.gateways.outputs.http.resources;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Getter
@RequiredArgsConstructor
public class CreateContactRequestProperties {

  @JsonProperty("email")
  private final String email;

  @JsonProperty("firstname")
  private final String firstName;

  @JsonProperty("lastname")
  private final String lastName;

  public CreateContactRequestProperties(@NonNull final Contact contact) {
    this.email = contact.getEmail();
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
  }
}
