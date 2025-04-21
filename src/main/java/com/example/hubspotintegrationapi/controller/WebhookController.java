package com.example.hubspotintegrationapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@Slf4j
@RequiredArgsConstructor
public class WebhookController {

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> handleWebhook(
      @RequestHeader("X-HubSpot-Signature") String signature,
      @RequestHeader("X-HubSpot-Request-Timestamp") Long timestamp,
      @RequestBody String rawBody,
      HttpServletRequest request) {

    if (!isValidTimestamp(timestamp) || !isValidSignature(signature, request, timestamp, rawBody)) {
      throw new RuntimeException("Invalid signature");
    }

    try {
      log.info("Webhook recebido: {}", rawBody);
    } catch (Exception e) {
      log.error("Erro ao processar webhook", e);
    }
    return ResponseEntity.ok().build();
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
