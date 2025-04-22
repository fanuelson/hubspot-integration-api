package com.example.hubspotintegrationapi.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  CONTACT_EMAIL_ALREADY_EXISTS("Contact with email %s already exists");

  private final String messageTemplate;
}
