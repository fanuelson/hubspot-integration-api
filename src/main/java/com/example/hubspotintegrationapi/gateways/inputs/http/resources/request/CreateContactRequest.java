package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.annotations.ContactEmailNonExistent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateContactRequest {

  @NotBlank
  @Email
  @ContactEmailNonExistent
  private final String email;

  @NotBlank
  private final String firstName;

  @NotBlank
  private final String lastName;

  public Contact toDomain() {
    return Contact.builder().email(email).firstName(firstName).lastName(lastName).build();
  }
}
