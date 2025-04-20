package com.example.hubspotintegrationapi.controller.exception.handler;

import com.example.hubspotintegrationapi.controller.exception.HubspotException;
import com.example.hubspotintegrationapi.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleInternalServerException(final Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<String> handleBusinessException(final BusinessException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({HubspotException.class})
  public ResponseEntity<String> handleHubspotException(final HubspotException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler({OAuth2AuthorizationException.class})
  public ResponseEntity<String> handleOAuth2AuthorizationException(
      final OAuth2AuthorizationException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }
}
