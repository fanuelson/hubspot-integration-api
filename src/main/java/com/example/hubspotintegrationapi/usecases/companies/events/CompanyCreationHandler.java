package com.example.hubspotintegrationapi.usecases.companies.events;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.usecases.events.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyCreationHandler implements EventHandler {

  @Override
  public void handle(@NonNull final EventPayload payload) {
    log.info("CompanyCreationHandler, payload {}", payload);
  }
}
