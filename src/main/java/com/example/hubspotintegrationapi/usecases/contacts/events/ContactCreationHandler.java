package com.example.hubspotintegrationapi.usecases.contacts.events;

import com.example.hubspotintegrationapi.usecases.events.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContactCreationHandler implements EventHandler {

  @Override
  public void handle(String payload) {
    log.info("ContactCreationHandler, payload {}", payload);
  }
}
