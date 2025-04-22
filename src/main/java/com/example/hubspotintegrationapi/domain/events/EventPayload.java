package com.example.hubspotintegrationapi.domain.events;

import lombok.Getter;

@Getter
public class EventPayload {
  private final EventType eventType;
  private final String eventId;

  public EventPayload(final EventType eventType, final String eventId) {
    this.eventType = eventType;
    this.eventId = eventId;
  }
}
