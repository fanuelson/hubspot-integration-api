package com.example.hubspotintegrationapi.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import org.springframework.lang.NonNull;

@Getter
@With
@ToString
@NoArgsConstructor
public class EventPayload {

  private EventHeaders eventHeaders;
  private EventType eventType;
  private String eventId;
  private String objectId;

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
