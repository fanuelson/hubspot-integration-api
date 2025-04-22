package com.example.hubspotintegrationapi.config.restclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rest-client.hubspot")
@EnableConfigurationProperties
@Data
public class HubspotRestClientProp {
  private String apiUrl;
  private String contactsResource;
}
