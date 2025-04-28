package com.example.hubspotintegrationapi.gateways.inputs.http.resources.request;

import com.example.hubspotintegrationapi.domain.events.EventHeaders;
import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.domain.events.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@With
public class WebhookPayload {

  private Map<String, String> eventHeaders;
  private String subscriptionType;
  private String eventId;
  private String objectId;

  public EventPayload toDomain() {
    return new EventPayload(new EventHeaders(eventHeaders), EventType.get(this.subscriptionType), eventId, objectId);
  }
}
