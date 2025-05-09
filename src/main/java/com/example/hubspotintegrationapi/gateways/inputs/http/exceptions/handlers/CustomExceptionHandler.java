package com.example.hubspotintegrationapi.gateways.inputs.http.exceptions.handlers;

import com.example.hubspotintegrationapi.exceptions.BusinessValidationException;
import com.example.hubspotintegrationapi.exceptions.NotFoundException;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.ErrorResponse;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.HubspotErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleInternalServerException(final Exception ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<ErrorResponse> handleNoResourceFoundException(final NoResourceFoundException ex) {
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase()), HttpStatus.NOT_FOUND);
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

  @ExceptionHandler({BusinessValidationException.class})
  public ResponseEntity<ErrorResponse> handleBusinessValidationException(
      final BusinessValidationException ex) {
    log(ex);
    return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(final MethodArgumentNotValidException ex) {
    log(ex);
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(field -> "%s: %s".formatted(field.getField(), field.getDefaultMessage()))
            .toList();

    return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }

  private void log(final Exception ex) {
    log.error(ex.getMessage(), ex);
  }

  private ErrorResponse createErrorResponse(final Throwable ex) {
    return new ErrorResponse(ex.getMessage());
  }

  private ErrorResponse createErrorResponse(final OAuth2AuthorizationException ex) {
    return new ErrorResponse(ex.getError().toString());
  }

  private ErrorResponse createErrorResponse(final BusinessValidationException ex) {
    return new ErrorResponse(ex.getErrors());
  }
}
