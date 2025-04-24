package com.example.hubspotintegrationapi.usecases.oauth2;

import com.example.hubspotintegrationapi.config.restclient.HubspotRestClientProp;
import com.example.hubspotintegrationapi.domain.oauth2.OAuth2CallbackContext;
import com.example.hubspotintegrationapi.exceptions.HubSpotOAuth2AuthorizationException;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.OAuth2TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class OAuth2AuthorizeClient {

  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientRepository authorizedClientRepository;

  public void execute(@NonNull final OAuth2CallbackContext oAuth2CallbackContext) {
    if (oAuth2CallbackContext.hasError()) {
      throw new HubSpotOAuth2AuthorizationException(oAuth2CallbackContext.getError());
    }

    val clientRegistration = getClientRegistrationHubSpot();
    val redirectUriTemplate = clientRegistration.getRedirectUri();
    val tokenUri = clientRegistration.getProviderDetails().getTokenUri();

    val baseUrl = oAuth2CallbackContext.getBaseUrl();
    val redirectUri = redirectUriTemplate.replace("{baseUrl}", baseUrl);

    val body = mountBody(clientRegistration, redirectUri, oAuth2CallbackContext.getCode());
    val oAuth2TokenResponse = exchangeForTokens(tokenUri, body);
    val accessToken = oAuth2TokenResponse.toOAuth2AccessToken();
    val refreshToken = oAuth2TokenResponse.toOAuth2RefreshToken();

    // Cria o OAuth2AuthorizedClient
    val principal = SecurityContextHolder.getContext().getAuthentication();
    val authorizedClient =
        new OAuth2AuthorizedClient(
            clientRegistration, principal.getName(), accessToken, refreshToken);

    // Salva o OAuth2AuthorizedClient no contexto de segurança
    authorizedClientRepository.saveAuthorizedClient(
        authorizedClient,
        principal,
        oAuth2CallbackContext.getRequest(),
        oAuth2CallbackContext.getResponse());
  }

  private ClientRegistration getClientRegistrationHubSpot() {
    return clientRegistrationRepository.findByRegistrationId(HubspotRestClientProp.REGISTRATION_ID);
  }

  private MultiValueMap<String, String> mountBody(
      final ClientRegistration clientRegistration, final String redirectUri, final String code) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    body.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
    body.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
    body.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
    body.add(OAuth2ParameterNames.CODE, code);
    return body;
  }

  private OAuth2TokenResponse exchangeForTokens(
      final String tokenUri, final MultiValueMap<String, String> body) {
    return RestClient.create(tokenUri)
        .post()
        .uri(tokenUri)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(body)
        .retrieve()
        .body(OAuth2TokenResponse.class);
  }
}
