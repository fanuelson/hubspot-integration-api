package com.example.hubspotintegrationapi.config.restclient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties("rest-client.hubspot")
@Validated
public class HubspotRestClientProp {

  public static final String REGISTRATION_ID = "hubspot";
  @NotBlank private String apiUrl;
  @NotBlank private String contactsResource;
}
