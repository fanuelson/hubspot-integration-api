package com.example.hubspotintegrationapi.gateways.outputs.kafka.producers;

import com.example.hubspotintegrationapi.config.kafka.TopicProperties;
import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.gateways.outputs.EventMessageGateway;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventMessageGatewayImpl implements EventMessageGateway {
  private final BaseProducer baseProducer;
  private final TopicProperties topicProperties;

  public void sendMessageProcessor(@NonNull final EventPayload eventPayload) {
    val topic = topicProperties.getHubspotWebhookProcessor();
    val key = eventPayload.getEventType().toString();
    val json = JsonUtils.toJson(eventPayload);
    baseProducer.sendMessage(topic, key, json);
  }
}
