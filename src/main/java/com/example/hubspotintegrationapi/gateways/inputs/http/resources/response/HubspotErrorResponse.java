package com.example.hubspotintegrationapi.gateways.inputs.http.resources.response;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;

@Getter
public class HubspotErrorResponse {

  private final Map<String, Serializable> error;

  public HubspotErrorResponse(@NonNull final HttpClientErrorException exception) {
    this.error =
        exception.getResponseBodyAs(new ParameterizedTypeReference<Map<String, Serializable>>() {});
  }
}
