package com.example.hubspotintegrationapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
@Slf4j
public class HubspotWebhookController {

  @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
  private String clientSecret;

  @PostMapping("/hubspot")
  public ResponseEntity<Void> handleWebhook(
      @RequestHeader("X-HubSpot-Signature") String signature,
      @RequestHeader("X-HubSpot-Request-Timestamp") Long timestamp,
      @RequestBody String rawBody,
      HttpServletRequest request) {

    if (!isValidTimestamp(timestamp) || !isValidSignature(signature, timestamp, rawBody)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ObjectMapper mapper = new ObjectMapper();
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

  private boolean isValidSignature(String signature, Long timestamp, String body) {
    try {
      String base = timestamp + "." + body;
      Mac hmacSha256 = Mac.getInstance("HmacSHA256");
      SecretKeySpec keySpec =
          new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      hmacSha256.init(keySpec);
      byte[] rawHmac = hmacSha256.doFinal(base.getBytes(StandardCharsets.UTF_8));
      String expected = Base64.getEncoder().encodeToString(rawHmac);
      return expected.equals(signature);
    } catch (Exception e) {
      return false;
    }
  }
}
