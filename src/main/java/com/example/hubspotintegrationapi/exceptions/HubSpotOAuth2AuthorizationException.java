package com.example.hubspotintegrationapi.exceptions;

import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class HubSpotOAuth2AuthorizationException extends OAuth2AuthorizationException {
  public HubSpotOAuth2AuthorizationException(@NonNull final String message) {
    super(new OAuth2Error("error", message, ""));
  }
}
