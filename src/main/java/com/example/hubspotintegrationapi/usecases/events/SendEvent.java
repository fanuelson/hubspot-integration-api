package com.example.hubspotintegrationapi.usecases.events;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.gateways.outputs.HubSpotEventMessageGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEvent {
  private final HubSpotEventMessageGateway hubSpotEventMessageGateway;

  public void execute(@NonNull final EventPayload eventPayload) {
    this.hubSpotEventMessageGateway.sendHubSpotWebhookProcessor(eventPayload);
  }
}
