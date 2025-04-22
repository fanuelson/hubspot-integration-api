package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateContactRequest {

  private String email;
  private String firstName;
  private String lastName;

  public Contact toDomain() {
    return new Contact().withEmail(email).withFirstName(firstName).withLastName(lastName);
  }
}
