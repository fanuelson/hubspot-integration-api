package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.events.EventType;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.WebhookPayload;
import com.example.hubspotintegrationapi.usecases.events.GetEventHandler;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@Slf4j
@RequiredArgsConstructor
public class WebhookController {

  private final GetEventHandler getEventHandler;

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void handleWebhook(
      @RequestHeader("X-HubSpot-Signature") String signature,
      @RequestHeader("X-HubSpot-Request-Timestamp") Long timestamp,
      @RequestBody String rawBody,
      HttpServletRequest request) {
    log.info("Webhook recebido: {}", rawBody);
    if (!isValidTimestamp(timestamp) || !isValidSignature(signature, request, timestamp, rawBody)) {
      throw new RuntimeException("Invalid signature");
    }

    val optionalPayloads =
        JsonUtils.toObject(rawBody, new TypeReference<List<WebhookPayload>>() {});

    try {
      optionalPayloads.ifPresent(
          webhookPayloads ->
              webhookPayloads.forEach(
                  (webhookPayload -> {
                    EventType.get(webhookPayload.getSubscriptionType())
                        .ifPresent(
                            (eventType -> {
                              val handler = getEventHandler.execute(eventType);
                              handler.handle(rawBody);
                            }));
                  })));

    } catch (Exception e) {
      log.error("Erro ao processar webhook", e);
    }
  }

  private boolean isValidTimestamp(final Long timestamp) {
    val durationInMinutes =
        ChronoUnit.MINUTES.between(Instant.ofEpochMilli(timestamp), Instant.now());
    return Math.abs(durationInMinutes) < 5;
  }

  private boolean isValidSignature(
      String signature, HttpServletRequest request, Long timestamp, String rawBody) {
    // TODO: Implementar validação de segurança do hubspot
    return true;
  }
}
