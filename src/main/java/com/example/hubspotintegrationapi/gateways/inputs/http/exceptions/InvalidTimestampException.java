package com.example.hubspotintegrationapi.gateways.inputs.http.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class InvalidTimestampException extends ResponseStatusException {

  public InvalidTimestampException() {
    super(HttpStatus.FORBIDDEN, "Timestamp too old");
  }
}
