package com.example.hubspotintegrationapi.config.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

  private static final String CLIENT_REGISTRATION_ID = "hubspot";
  private final HubspotRestClientProp hubspotRestClientProp;

  @Bean
  public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
    OAuth2ClientHttpRequestInterceptor interceptor =
        new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
    return RestClient.builder()
        .requestInitializer(
            request ->
                RequestAttributeClientRegistrationIdResolver.clientRegistrationId(
                        CLIENT_REGISTRATION_ID)
                    .accept(request.getAttributes()))
        .requestInterceptor(interceptor)
        .baseUrl(hubspotRestClientProp.getApiUrl())
        .build();
  }
}
