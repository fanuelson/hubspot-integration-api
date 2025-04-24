package com.example.hubspotintegrationapi.usecases.oauth2;

import com.example.hubspotintegrationapi.config.restclient.HubspotRestClientProp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenerateOAuth2AuthorizationUrl {

  private final ClientRegistrationRepository clientRegistrationRepository;

  public String execute(final HttpServletRequest request) {
    val registration = getClientRegistrationHubSpot();
    val baseUrl = getBaseUrl(request);
    // TODO: tentar pegar baseUrl pelo request
    val redirectUri = registration.getRedirectUri().replace("{baseUrl}", baseUrl);
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

  private String getBaseUrl(final HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
  }

  private ClientRegistration getClientRegistrationHubSpot() {
    return clientRegistrationRepository.findByRegistrationId(HubspotRestClientProp.registrationId);
  }
}
