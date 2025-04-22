package com.example.hubspotintegrationapi.gateways.inputs.http.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class InvalidSignatureException extends ResponseStatusException {

  public InvalidSignatureException() {
    super(HttpStatus.FORBIDDEN, "Invalid signature");
  }
}
