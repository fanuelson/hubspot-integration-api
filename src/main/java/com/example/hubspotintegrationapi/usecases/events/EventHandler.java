package com.example.hubspotintegrationapi.usecases.events;

import com.example.hubspotintegrationapi.domain.events.EventPayload;

public interface EventHandler {
  void handle(EventPayload payload);
}
