package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;

@Getter
public class HubspotErrorResponse extends ErrorResponse {

  private final Map<String, Serializable> hubSpotErrorBody;

  public HubspotErrorResponse(final HttpClientErrorException httpClientErrorException) {
    super(httpClientErrorException.getMessage());
    this.hubSpotErrorBody =
        httpClientErrorException.getResponseBodyAs(
            new ParameterizedTypeReference<Map<String, Serializable>>() {});
  }
}
