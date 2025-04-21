package com.example.hubspotintegrationapi.controller.exception.handler;

import com.example.hubspotintegrationapi.controller.exception.HubspotException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleInternalServerException(final Exception ex) {
    log(ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({HubspotException.class})
  public ResponseEntity<String> handleHubspotException(final HubspotException ex) {
    log(ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler({OAuth2AuthorizationException.class})
  public ResponseEntity<String> handleOAuth2AuthorizationException(
      final OAuth2AuthorizationException ex) {
    log(ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  private static void log(Exception ex) {
    log.error(ex.getMessage());
  }
}
