package com.example.hubspotintegrationapi.domain.events;

import lombok.Getter;
import lombok.ToString;
import lombok.With;
import org.springframework.lang.NonNull;

@Getter
@With
@ToString
public class EventPayload {

  private final EventHeaders eventHeaders;
  private final EventType eventType;
  private final String eventId;
  private final String objectId;

  public EventPayload(
      @NonNull final EventHeaders eventHeaders,
      @NonNull final EventType eventType,
      @NonNull final String eventId,
      @NonNull final String objectId) {
    this.eventHeaders = eventHeaders;
    this.eventType = eventType;
    this.eventId = eventId;
    this.objectId = objectId;
  }
}
