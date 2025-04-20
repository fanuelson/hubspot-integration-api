package com.example.hubspotintegrationapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final RestClient restClient;

    @Value("${spring.security.oauth2.client.registration.hubspot.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.hubspot.redirect-uri}")
    private String redirectUriTemplate;

    @GetMapping("/authorize")
    public ResponseEntity<String> authorize(HttpServletRequest request) {
        ClientRegistration registration = clientRegistrationRepository.findByRegistrationId("hubspot");

        String baseUrl = getBaseUrl(request);
        String redirectUri = registration.getRedirectUri().replace("{baseUrl}", baseUrl);

        String authorizationUrl = UriComponentsBuilder
                .fromUriString(registration.getProviderDetails().getAuthorizationUri())
                .queryParam("client_id", registration.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", registration.getScopes()))
                .build()
                .toUriString();

        return ResponseEntity.ok(authorizationUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        String baseUrl = getBaseUrl(request);
        String redirectUri = redirectUriTemplate.replace("{baseUrl}", baseUrl);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        try {
            TokenResponse tokenResponse = restClient.post()
                    .uri("https://api.hubapi.com/oauth/v1/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .body(TokenResponse.class);

            // Obter o ClientRegistration
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("hubspot");

            // Criar o OAuth2AccessToken
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    tokenResponse.getAccessToken(),
                    Instant.now(),
                    Instant.now().plusSeconds(tokenResponse.getExpiresIn())
            );

            // Criar o OAuth2AuthorizedClient
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
                    clientRegistration,
                    principal.getName(),
                    accessToken
            );

            // Salvar o OAuth2AuthorizedClient no contexto de segurança
            authorizedClientRepository.saveAuthorizedClient(authorizedClient, principal, request, response);

            return ResponseEntity.ok(tokenResponse);

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
