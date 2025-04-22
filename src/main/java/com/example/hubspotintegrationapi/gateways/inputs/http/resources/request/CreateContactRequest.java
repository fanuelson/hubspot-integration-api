package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.Contact;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateContactRequest {

  @NotBlank private final String email;
  private final String firstName;
  private final String lastName;

  public Contact toDomain() {
    return Contact.builder().email(email).firstName(firstName).lastName(lastName).build();
  }
}
