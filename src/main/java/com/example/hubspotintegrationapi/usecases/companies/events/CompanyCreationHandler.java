package com.example.hubspotintegrationapi.usecases.companies.events;

import com.example.hubspotintegrationapi.usecases.events.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyCreationHandler implements EventHandler {

  @Override
  public void handle(String payload) {
    log.info("CompanyCreationHandler, payload {}", payload);
  }
}
