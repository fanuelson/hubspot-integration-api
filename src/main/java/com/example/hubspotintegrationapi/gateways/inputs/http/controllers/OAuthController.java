package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.OAuth2TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
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

  private static final String REGISTRATION_ID = "hubspot";

  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientRepository authorizedClientRepository;

  @GetMapping("/authorize")
  @ResponseStatus(HttpStatus.OK)
  public String authorize(HttpServletRequest request) {
    ClientRegistration registration = getClientRegistrationHubSpot();
    String baseUrl = getBaseUrl(request);
    String redirectUri = registration.getRedirectUri().replace("{baseUrl}", baseUrl);
    // TODO: create validation
    Objects.requireNonNull(registration.getScopes(), "Missing scopes");
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
  public void callback(
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String error,
      @RequestParam(required = false) String errorDescription,
      HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    // TODO: create validation
    if (Objects.nonNull(error)) {
      response.getWriter().println(error);
      return;
    }

    ClientRegistration registration = getClientRegistrationHubSpot();
    val redirectUriTemplate = registration.getRedirectUri();
    val clientId = registration.getClientId();
    val clientSecret = registration.getClientSecret();
    val tokenUri = registration.getProviderDetails().getTokenUri();

    String baseUrl = getBaseUrl(request);
    String redirectUri = redirectUriTemplate.replace("{baseUrl}", baseUrl);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    body.add(OAuth2ParameterNames.CLIENT_ID, clientId);
    body.add(OAuth2ParameterNames.CLIENT_SECRET, clientSecret);
    body.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
    body.add(OAuth2ParameterNames.CODE, code);

    OAuth2TokenResponse oAuth2TokenResponse =
        RestClient.create(tokenUri)
            .post()
            .uri(tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(OAuth2TokenResponse.class);

    ClientRegistration clientRegistration = getClientRegistrationHubSpot();

    OAuth2AccessToken accessToken =
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            oAuth2TokenResponse.getAccessToken(),
            Instant.now(),
            Instant.now().plusSeconds(oAuth2TokenResponse.getExpiresIn()));

    OAuth2RefreshToken refreshToken =
        new OAuth2RefreshToken(oAuth2TokenResponse.getRefreshToken(), Instant.now());

    // Cria o OAuth2AuthorizedClient
    Authentication principal = SecurityContextHolder.getContext().getAuthentication();
    OAuth2AuthorizedClient authorizedClient =
        new OAuth2AuthorizedClient(
            clientRegistration, principal.getName(), accessToken, refreshToken);

    // Salva o OAuth2AuthorizedClient no contexto de segurança
    authorizedClientRepository.saveAuthorizedClient(authorizedClient, principal, request, response);

    response.sendRedirect(request.getContextPath() + "/swagger-ui/index.html");
  }

  private String getBaseUrl(HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
  }

  private ClientRegistration getClientRegistrationHubSpot() {
    return clientRegistrationRepository.findByRegistrationId(REGISTRATION_ID);
  }
}
