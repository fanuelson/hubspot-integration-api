package com.example.hubspotintegrationapi.domain.events;

import lombok.Getter;
import lombok.ToString;
import lombok.With;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;

@Getter
@With
@ToString
public class EventPayload implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final EventType eventType;
  private final String eventId;

  public EventPayload(@NonNull final EventType eventType, @NonNull final String eventId) {
    this.eventType = eventType;
    this.eventId = eventId;
  }
}
