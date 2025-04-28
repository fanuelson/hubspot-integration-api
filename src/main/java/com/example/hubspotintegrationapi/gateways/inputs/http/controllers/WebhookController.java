package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.context.HubSpotHeaderNames;
import com.example.hubspotintegrationapi.domain.events.EventType;
import com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.InvalidSignatureException;
import com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.InvalidTimestampException;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.request.WebhookPayload;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.SignatureValidatorV1;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.TimestampValidator;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.context.SignatureV1ValidationContext;
import com.example.hubspotintegrationapi.gateways.inputs.http.validators.context.TimestampValidationContext;
import com.example.hubspotintegrationapi.usecases.events.GetEventHandler;
import com.example.hubspotintegrationapi.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WebhookController {

  private final GetEventHandler getEventHandler;

  private final SignatureValidatorV1 signatureValidatorV1;
  private final TimestampValidator timestampValidator;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void handleWebhook(
      @RequestHeader(HubSpotHeaderNames.SIGNATURE) final String signature,
      @RequestHeader(HubSpotHeaderNames.REQUEST_TIMESTAMP) final Long timestamp,
      @RequestBody final String rawBody,
      HttpServletRequest request) {
    log.info("Webhook received: {}", rawBody);

    timestampValidator
        .validate(new TimestampValidationContext(timestamp))
        .ifPresent(
            validationError -> {
              throw new InvalidTimestampException();
            });
    signatureValidatorV1
        .validate(new SignatureV1ValidationContext(signature, rawBody))
        .ifPresent(
            validationError -> {
              throw new InvalidSignatureException();
            });

    val optionalPayloads =
        JsonUtils.toObject(rawBody, new TypeReference<List<WebhookPayload>>() {});

    val headers = new HashMap<String, String>();
    request
        .getHeaderNames()
        .asIterator()
        .forEachRemaining(headerName -> headers.put(headerName, request.getHeader(headerName)));
    try {
      optionalPayloads.ifPresent(
          webhookPayloads ->
              webhookPayloads.forEach(
                  (webhookPayload ->
                      EventType.getOptional(webhookPayload.getSubscriptionType())
                          .ifPresent(
                              (eventType -> {
                                val handler = getEventHandler.execute(eventType);
                                handler.handle(webhookPayload.withEventHeaders(headers).toDomain());
                              })))));

    } catch (Exception e) {
      log.error("Erro ao processar webhook", e);
    }
  }
}
