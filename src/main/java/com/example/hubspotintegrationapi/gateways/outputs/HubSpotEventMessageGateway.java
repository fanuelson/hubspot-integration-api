package com.example.hubspotintegrationapi.gateways.outputs;

import com.example.hubspotintegrationapi.domain.events.EventPayload;

public interface HubSpotEventMessageGateway {

  void sendHubSpotWebhookProcessor(final EventPayload eventPayload);
}
