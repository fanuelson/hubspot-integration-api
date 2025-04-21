package com.example.hubspotintegrationapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OAuth2TokenHubSpotResponse {
  @JsonProperty("access_token")
  private final String accessToken;

  @JsonProperty("refresh_token")
  private final String refreshToken;

  @JsonProperty("expires_in")
  private final int expiresIn;
}
