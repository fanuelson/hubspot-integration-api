package com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.handler;

import com.example.hubspotintegrationapi.exceptions.NotFoundException;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.ErrorResponse;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.HubspotErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  private void log(Exception ex) {
    log.error(ex.getMessage());
  }

  private ErrorResponse createErrorResponse(final Throwable ex) {
    return new ErrorResponse(ex.getMessage());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleInternalServerException(final Exception ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<HubspotErrorResponse> handleHttpClientErrorException(
      final HttpClientErrorException ex) {
    log(ex);
    return new ResponseEntity<>(new HubspotErrorResponse(ex), ex.getStatusCode());
  }

  @ExceptionHandler({OAuth2AuthorizationException.class})
  public ResponseEntity<ErrorResponse> handleOAuth2AuthorizationException(
      final OAuth2AuthorizationException ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<ErrorResponse> handleResponseStatusException(
          final ResponseStatusException ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), ex.getStatusCode());
  }
}
