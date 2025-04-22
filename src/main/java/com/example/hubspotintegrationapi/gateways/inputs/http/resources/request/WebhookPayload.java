package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.domain.events.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Getter
public class WebhookPayload {
  private final String subscriptionType;
  private final String eventId;

  public EventPayload toDomain(@NonNull final EventPayload eventPayload) {
    return eventPayload
        .withEventType(EventType.get(this.subscriptionType))
        .withEventId(this.eventId);
  }

  public EventPayload toDomain() {
    return new EventPayload(EventType.get(this.subscriptionType), eventId);
  }
}
