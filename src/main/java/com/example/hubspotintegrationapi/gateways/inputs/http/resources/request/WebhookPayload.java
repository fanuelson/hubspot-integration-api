package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.domain.events.EventType;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebhookPayload implements Serializable {

  private String subscriptionType;
  private String eventId;

  public EventPayload toDomain(@NonNull final EventPayload eventPayload) {
    return eventPayload
        .withEventType(EventType.get(this.subscriptionType))
        .withEventId(this.eventId);
  }

  public EventPayload toDomain() {
    return new EventPayload(EventType.get(this.subscriptionType), eventId);
  }
}
