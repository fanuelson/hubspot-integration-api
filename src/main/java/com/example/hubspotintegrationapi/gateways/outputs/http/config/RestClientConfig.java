package com.example.hubspotintegrationapi.gateways.outputs.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String HUBSPOT_BASE_URL = "https://api.hubapi.com";
    private static final String CLIENT_REGISTRATION_ID = "hubspot";

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
                .baseUrl(HUBSPOT_BASE_URL)
                .build();
    }
}
