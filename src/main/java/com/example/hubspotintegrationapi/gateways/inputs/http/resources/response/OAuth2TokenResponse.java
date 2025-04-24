package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

@RequiredArgsConstructor
@Getter
public class OAuth2TokenResponse {

  @JsonProperty("access_token")
  private final String accessToken;

  @JsonProperty("refresh_token")
  private final String refreshToken;

  @JsonProperty("expires_in")
  private final int expiresIn;

  public OAuth2AccessToken toOAuth2AccessToken() {
    return new OAuth2AccessToken(
        OAuth2AccessToken.TokenType.BEARER,
        accessToken,
        Instant.now(),
        Instant.now().plusSeconds(expiresIn));
  }

  public OAuth2RefreshToken toOAuth2RefreshToken() {
    return new OAuth2RefreshToken(refreshToken, Instant.now());
  }
}
