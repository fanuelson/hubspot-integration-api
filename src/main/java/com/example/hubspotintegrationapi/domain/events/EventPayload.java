package com.example.hubspotintegrationapi.domain.events;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import org.springframework.lang.NonNull;

@Getter
@With
@ToString
public class EventPayload implements Serializable {

  private final EventType eventType;
  private final String eventId;

  public EventPayload(@NonNull final EventType eventType, @NonNull final String eventId) {
    this.eventType = eventType;
    this.eventId = eventId;
  }
}
