package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.events.EventType;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.request.WebhookPayload;
import com.example.hubspotintegrationapi.usecases.events.GetEventHandler;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@Slf4j
@RequiredArgsConstructor
public class WebhookController {

  private final GetEventHandler getEventHandler;

  @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
  private String clientSecret;

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void handleWebhook(
      @RequestHeader("X-HubSpot-Signature-v3") String signatureV3,
      @RequestHeader("X-HubSpot-Request-Timestamp") Long timestamp,
      @RequestBody String rawBody,
      HttpServletRequest request) {
    log.info("Webhook recebido: {}", rawBody);

    if (!isValidTimestamp(timestamp)
        || !isValidSignature(signatureV3, request, timestamp, rawBody)) {
      throw new RuntimeException("Invalid signature");
    }

    val optionalPayloads =
        JsonUtils.toObject(rawBody, new TypeReference<List<WebhookPayload>>() {});

    try {
      optionalPayloads.ifPresent(
          webhookPayloads ->
              webhookPayloads.forEach(
                  (webhookPayload -> {
                    EventType.getOptional(webhookPayload.getSubscriptionType())
                        .ifPresent(
                            (eventType -> {
                              val handler = getEventHandler.execute(eventType);
                              handler.handle(webhookPayload.toDomain());
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
      String signatureV3, HttpServletRequest request, Long timestamp, String rawBody) {
    // Monta a URI original
    String uri = request.getRequestURL().toString();
    // String que será assinada
    String rawString = request.getMethod() + uri + rawBody + timestamp;

    // Gera o hash com HMAC SHA256 + base64
    String hashedString = generateHmacSHA256Base64(rawString, clientSecret);

    // FIXME: Not working
    val isValidSignature = MessageDigest.isEqual(hashedString.getBytes(), signatureV3.getBytes());
    return true;
  }

  private String generateHmacSHA256Base64(String data, String secret) {
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
      sha256_HMAC.init(secretKey);
      byte[] hashBytes = sha256_HMAC.doFinal(data.getBytes());
      return Base64.getEncoder().encodeToString(hashBytes);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao gerar HMAC SHA256", e);
    }
  }
}
