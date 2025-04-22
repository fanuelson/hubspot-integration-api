package com.example.hubspotintegrationapi.domain.events;

import lombok.Getter;
import lombok.With;
import org.springframework.lang.NonNull;

@Getter
@With
public class EventPayload {
  private final EventType eventType;
  private final String eventId;

  public EventPayload(@NonNull final EventType eventType, @NonNull final String eventId) {
    this.eventType = eventType;
    this.eventId = eventId;
  }
}
