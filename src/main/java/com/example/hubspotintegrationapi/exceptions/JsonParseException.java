package com.example.hubspotintegrationapi.exceptions;

public class JsonParseException extends RuntimeException {

  public JsonParseException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
