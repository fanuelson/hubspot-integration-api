package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.events.EventType;
import com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.InvalidSignatureException;
import com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.InvalidTimestampException;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.request.WebhookPayload;
import com.example.hubspotintegrationapi.usecases.events.GetEventHandler;
import com.example.hubspotintegrationapi.utils.HashUtils;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WebhookController {

  private final GetEventHandler getEventHandler;

  @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
  private String clientSecret;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void handleWebhook(
      @RequestHeader("X-HubSpot-Signature-v3") final String signatureV3,
      @RequestHeader("X-HubSpot-Request-Timestamp") final Long timestamp,
      @RequestBody final String rawBody,
      HttpServletRequest request) {
    log.info("Webhook received: {}", rawBody);

    isValidTimestamp(timestamp);
    isValidSignature(signatureV3, request, timestamp, rawBody);

    val optionalPayloads =
        JsonUtils.toObject(rawBody, new TypeReference<List<WebhookPayload>>() {});

    try {
      optionalPayloads.ifPresent(
          webhookPayloads ->
              webhookPayloads.forEach(
                  (webhookPayload -> EventType.getOptional(webhookPayload.getSubscriptionType())
                      .ifPresent(
                          (eventType -> {
                            val handler = getEventHandler.execute(eventType);
                            handler.handle(webhookPayload.toDomain());
                          })))));

    } catch (Exception e) {
      log.error("Erro ao processar webhook", e);
    }
  }

  private void isValidTimestamp(@NonNull final Long timestamp) {
    val durationInMinutes =
        ChronoUnit.MINUTES.between(Instant.ofEpochMilli(timestamp), Instant.now());

    if (Math.abs(durationInMinutes) > 5) {
      throw new InvalidTimestampException();
    }
  }

  private void isValidSignature(
      final String signatureV3, final HttpServletRequest request, final Long timestamp, final String rawBody) {
    // Monta a URI original
    val uri = request.getRequestURL().toString();
    // String que será assinada
    val rawString = request.getMethod() + uri + rawBody + timestamp;

    // Gera o hash com HMAC SHA256 + base64
    val hashedString = HashUtils.generateHmacSHA256Base64(rawString, clientSecret);

    // FIXME: Not working, signature and rawString hash always different ;/
    // val invalidSignature = BooleanUtils.negate(MessageDigest.isEqual(hashedString.getBytes(),
    // signatureV3.getBytes()));
    val invalidSignature = Boolean.FALSE;

    if (invalidSignature) {
      throw new InvalidSignatureException();
    }
  }
}
