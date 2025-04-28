package com.example.hubspotintegrationapi.gateways.outputs;

import com.example.hubspotintegrationapi.domain.events.EventPayload;

public interface EventMessageGateway {

  void sendMessageProcessor(final EventPayload eventPayload);
}
