package com.example.hubspotintegrationapi.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  CONTACT_EMAIL_ALREADY_EXISTS("Contact with email %s already exists"),
  INVALID_TIMESTAMP("Timestamp too old: %s"),
  INVALID_V1_SIGNATURE("Invalid signature");

  private final String messageTemplate;
}
