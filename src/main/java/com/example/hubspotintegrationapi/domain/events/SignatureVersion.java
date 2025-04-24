package com.example.hubspotintegrationapi.domain.events;

import com.example.hubspotintegrationapi.exceptions.NotFoundException;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SignatureVersion {
  V1("v1"),
  V2("v2"),
  V3("v3");

  private final String version;

  public static Optional<SignatureVersion> getOptional(final String value) {
    return Stream.of(values())
        .filter(signatureVersion -> signatureVersion.getVersion().equalsIgnoreCase(value))
        .findFirst();
  }

  public static SignatureVersion get(final String value) {
    return SignatureVersion.getOptional(value)
        .orElseThrow(
            () -> new NotFoundException("Signature version: %s not found".formatted(value)));
  }
}
