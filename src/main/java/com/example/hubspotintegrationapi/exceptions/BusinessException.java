package com.example.hubspotintegrationapi.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }
}
