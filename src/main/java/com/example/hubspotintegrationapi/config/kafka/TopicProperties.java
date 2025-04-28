package com.example.hubspotintegrationapi.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("spring.kafka.topics")
public class TopicProperties {

  private String hubspotWebhookProcessor;
}
