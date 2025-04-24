package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.domain.events.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebhookPayload {

  private String subscriptionType;
  private String eventId;

  public EventPayload toDomain() {
    return new EventPayload(EventType.get(this.subscriptionType), eventId);
  }
}
