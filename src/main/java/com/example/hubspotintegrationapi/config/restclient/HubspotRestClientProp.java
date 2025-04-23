package com.example.hubspotintegrationapi.config.restclient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties("rest-client.hubspot")
@EnableConfigurationProperties
@Validated
public class HubspotRestClientProp {
  @NotBlank private String apiUrl;
  @NotBlank private String contactsResource;
}
