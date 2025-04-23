package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class CreateContactResponse {
  private final String id;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final LocalDateTime createdAt;

  public CreateContactResponse(@NonNull final Contact contact) {
    this.id = contact.getId();
    this.email = contact.getEmail();
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
    this.createdAt = contact.getCreatedAt();
  }
}
