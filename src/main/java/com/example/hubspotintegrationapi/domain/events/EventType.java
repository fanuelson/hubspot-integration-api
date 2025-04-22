package com.example.hubspotintegrationapi.domain.events;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
  CONTACT_CREATION("contact.creation"),
  COMPANY_CREATION("company.creation");

  private final String value;

  public static Optional<EventType> get(final String value) {
    return Stream.of(values())
        .filter(eventType -> eventType.getValue().equalsIgnoreCase(value))
        .findFirst();
  }
}
