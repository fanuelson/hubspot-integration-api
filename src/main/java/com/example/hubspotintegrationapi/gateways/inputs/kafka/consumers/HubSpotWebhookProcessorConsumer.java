package com.example.hubspotintegrationapi.gateways.inputs.kafka.consumers;

import com.example.hubspotintegrationapi.domain.events.EventPayload;
import com.example.hubspotintegrationapi.usecases.events.GetEventHandler;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubSpotWebhookProcessorConsumer extends BaseConsumer {

  private final GetEventHandler getEventHandler;

  @KafkaListener(
      topics = {"${spring.kafka.topics.hubspotWebhookProcessor}"},
      containerFactory = "kafkaListenerContainerFactory")
  public void consume(ConsumerRecord<String, String> consumerRecord) {
    val message = consumerRecord.value();

    super.log(consumerRecord);

    try {
      val eventPayload = JsonUtils.toObject(message, EventPayload.class);
      val handler = getEventHandler.execute(eventPayload.getEventType());
      handler.handle(eventPayload);
    } catch (final Exception exception) {
      log.error(
          "Kafka Consumer {}, Topic {} has failed for message {}.",
          getClass().getName(),
          consumerRecord.topic(),
          message,
          exception);
    }
  }
}
