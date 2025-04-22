package com.example.hubspotintegrationapi.usecases.contacts.events;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.usecases.events.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactCreationHandler implements EventHandler {

  @Override
  public void handle(@NonNull final EventPayload payload) {
    log.info("ContactCreationHandler, payload {}", payload);
  }
}
