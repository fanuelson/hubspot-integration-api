package com.example.hubspotintegrationapi.domain.events;

import java.util.Map;

import com.example.hubspotintegrationapi.domain.context.HubSpotHeaderNames;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@ToString
public class EventHeaders {

  private final SignatureVersion signatureVersion;
  private final String signature;
  private final String signatureV3;
  private final String timeoutMillis;
  private final String requestTimestamp;

  public EventHeaders(@NonNull final Map<String, String> headers) {
    this.signatureVersion = SignatureVersion.get(headers.get(HubSpotHeaderNames.SIGNATURE_VERSION));
    this.signature = headers.get(HubSpotHeaderNames.SIGNATURE);
    this.signatureV3 = headers.get(HubSpotHeaderNames.SIGNATURE_V3);
    this.timeoutMillis = headers.get(HubSpotHeaderNames.TIMEOUT_MILLIS);
    this.requestTimestamp = headers.get(HubSpotHeaderNames.REQUEST_TIMESTAMP);
  }
}
