package com.example.hubspotintegrationapi.usecases.events;

import com.example.hubspotintegrationapi.domain.events.EventType;
import com.example.hubspotintegrationapi.usecases.companies.events.CompanyCreationHandler;
import com.example.hubspotintegrationapi.usecases.contacts.events.ContactCreationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventHandler {

  private final ApplicationContext aplApplicationContext;

  public EventHandler execute(final EventType eventType) {
    return switch (eventType) {
      case EventType.CONTACT_CREATION ->
          aplApplicationContext.getBean(ContactCreationHandler.class);
      case EventType.COMPANY_CREATION ->
          aplApplicationContext.getBean(CompanyCreationHandler.class);
      default -> throw new RuntimeException("Handler not implemented for event: " + eventType);
    };
  }
}
