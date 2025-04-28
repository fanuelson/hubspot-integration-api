package com.example.hubspotintegrationapi.gateways.inputs.kafka.consumers;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.lang.NonNull;

@Slf4j
public abstract class BaseConsumer {

  protected void log(@NonNull final ConsumerRecord<String, String> consumerRecord) {

    log.info(
        "Message received from topic {} partitionId {} offset {} key {} message {}",
        consumerRecord.topic(),
        consumerRecord.partition(),
        consumerRecord.offset(),
        consumerRecord.key(),
        consumerRecord.value());
  }
}
