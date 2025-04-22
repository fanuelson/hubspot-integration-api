package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import com.example.hubspotintegrationapi.domain.Contact;
import lombok.Getter;

@Getter
public class CreateContactResponse {
  private final String id;
  private final String email;
  private final String firstName;
  private final String lastName;

  public CreateContactResponse(final Contact contact) {
    this.id = contact.getId();
    this.email = contact.getEmail();
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
  }
}
