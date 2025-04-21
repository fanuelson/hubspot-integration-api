package com.example.hubspotintegrationapi.controller;

import com.example.hubspotintegrationapi.constants.HubSpotConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientRepository authorizedClientRepository;

  @Value("${spring.security.oauth2.client.registration.hubspot.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
  private String clientSecret;

  @Value("${spring.security.oauth2.client.registration.hubspot.redirect-uri}")
  private String redirectUriTemplate;

  @Value("${spring.security.oauth2.client.provider.hubspot.token-uri}")
  private String tokenUri;

  @GetMapping("/authorize")
  @ResponseStatus(HttpStatus.OK)
  public String authorizationUrl(HttpServletRequest request) {
    ClientRegistration registration = getClientRegistrationHubSpot();
    String baseUrl = getBaseUrl(request);
    String redirectUri = registration.getRedirectUri().replace("{baseUrl}", baseUrl);

    return UriComponentsBuilder.fromUriString(
            registration.getProviderDetails().getAuthorizationUri())
        .queryParam(OAuth2ParameterNames.CLIENT_ID, registration.getClientId())
        .queryParam(OAuth2ParameterNames.REDIRECT_URI, redirectUri)
        .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, OAuth2ParameterNames.CODE)
        .queryParam(
            OAuth2ParameterNames.SCOPE, String.join(StringUtils.SPACE, registration.getScopes()))
        .build()
        .toUriString();
  }

  @GetMapping("/callback")
  @ResponseStatus(HttpStatus.OK)
  public OAuth2TokenHubSpotResponse callback(
      @RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
    String baseUrl = getBaseUrl(request);
    String redirectUri = redirectUriTemplate.replace("{baseUrl}", baseUrl);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    body.add(OAuth2ParameterNames.CLIENT_ID, clientId);
    body.add(OAuth2ParameterNames.CLIENT_SECRET, clientSecret);
    body.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
    body.add(OAuth2ParameterNames.CODE, code);

    OAuth2TokenHubSpotResponse oAuth2TokenHubSpotResponse =
        RestClient.create(tokenUri)
            .post()
            .uri(tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(OAuth2TokenHubSpotResponse.class);

    ClientRegistration clientRegistration = getClientRegistrationHubSpot();

    OAuth2AccessToken accessToken =
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            oAuth2TokenHubSpotResponse.getAccessToken(),
            Instant.now(),
            Instant.now().plusSeconds(oAuth2TokenHubSpotResponse.getExpiresIn()));

    OAuth2RefreshToken refreshToken =
        new OAuth2RefreshToken(oAuth2TokenHubSpotResponse.getRefreshToken(), Instant.now());

    // Cria o OAuth2AuthorizedClient
    Authentication principal = SecurityContextHolder.getContext().getAuthentication();
    OAuth2AuthorizedClient authorizedClient =
        new OAuth2AuthorizedClient(
            clientRegistration, principal.getName(), accessToken, refreshToken);

    // Salva o OAuth2AuthorizedClient no contexto de segurança
    authorizedClientRepository.saveAuthorizedClient(authorizedClient, principal, request, response);

    return oAuth2TokenHubSpotResponse;
  }

  private String getBaseUrl(HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
  }

  private ClientRegistration getClientRegistrationHubSpot() {
    return clientRegistrationRepository.findByRegistrationId(HubSpotConstants.HUBSPOT);
  }
}
