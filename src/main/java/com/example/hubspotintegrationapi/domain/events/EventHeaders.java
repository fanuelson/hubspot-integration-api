package com.example.hubspotintegrationapi.domain.events;

import java.util.Map;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class EventHeaders {

  private final SignatureVersion signatureVersion;
  private final String signature;
  private final String signatureV3;
  private final String timeoutMillis;
  private final String requestTimestamp;

  public EventHeaders(@NonNull final Map<String, String> headers) {
    this.signatureVersion = SignatureVersion.get(headers.get(EventHeaderNames.SIGNATURE_VERSION));
    this.signature = headers.get(EventHeaderNames.SIGNATURE);
    this.signatureV3 = headers.get(EventHeaderNames.SIGNATURE_V3);
    this.timeoutMillis = headers.get(EventHeaderNames.TIMEOUT_MILLIS);
    this.requestTimestamp = headers.get(EventHeaderNames.REQUEST_TIMESTAMP);
  }
}
