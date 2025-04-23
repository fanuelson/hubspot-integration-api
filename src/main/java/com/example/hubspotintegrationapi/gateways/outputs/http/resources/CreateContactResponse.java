package com.example.hubspotintegrationapi.gateways.outputs.http.resources;

import com.example.hubspotintegrationapi.domain.Contact;
import com.example.hubspotintegrationapi.domain.PropertiesWrapper;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
@AllArgsConstructor
public class CreateContactResponse extends PropertiesWrapper<LinkedHashMap<String, Object>> {
  private String id;
  private LocalDateTime createdAt;

  public Contact toDomain(@NonNull final Contact contact) {
    return contact
            .withId(this.id)
            .withCreatedAt(this.createdAt);
  }
}
