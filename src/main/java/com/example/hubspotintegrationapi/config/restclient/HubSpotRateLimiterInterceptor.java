package com.example.hubspotintegrationapi.config.restclient;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubSpotRateLimiterInterceptor implements ClientHttpRequestInterceptor {

  private final CircuitBreakerRegistry circuitBreakerRegistry;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    val response = execution.execute(request, body);
    logHubSpotResponseHeaders(response);
    // HubSpot retorna error code 429 quando ultrapassa o rate limit, documentação:
    // https://developers.hubspot.com/docs/guides/apps/api-usage/usage-details#error-responses
    if (HttpStatus.TOO_MANY_REQUESTS.isSameCodeAs(response.getStatusCode())) {
      log.error("HubSpot rate limit reached");
      circuitBreakerRegistry
          .circuitBreaker(CircuitBreakers.HUBSPOT_REST_CLIENT)
          .transitionToOpenState();
    }

    return response;
  }

  public void logHubSpotResponseHeaders(final ClientHttpResponse response) {
    val headers = response.getHeaders().asSingleValueMap();
    val hubSpotHeaders =
        headers.entrySet().stream()
            .filter(entry -> entry.getKey().toLowerCase().contains("hubspot"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    log.info("HubSpot Response Headers: {}", hubSpotHeaders);
  }
}
