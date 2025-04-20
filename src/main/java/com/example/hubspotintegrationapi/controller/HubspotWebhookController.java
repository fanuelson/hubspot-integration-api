package com.example.hubspotintegrationapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
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

    if (!isValidTimestamp(timestamp) || !isValidSignature(signature, request, timestamp, rawBody)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    try {
      String uri = request.getRequestURL().toString();

      // String que será assinada
      String rawString = request.getMethod() + uri + rawBody + timestamp;

      // Gera o hash com HMAC SHA256 + base64
      String hashedString = generateHmacSHA256Base64(rawString, clientSecret);

      return MessageDigest.isEqual(hashedString.getBytes(), signature.getBytes());
    } catch (Exception e) {
      return false;
    }
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
