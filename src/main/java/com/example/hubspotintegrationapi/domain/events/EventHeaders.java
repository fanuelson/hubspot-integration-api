package com.example.hubspotintegrationapi.domain.events;

import java.util.Map;

import com.example.hubspotintegrationapi.domain.context.HubSpotHeaderNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@ToString
@NoArgsConstructor
public class EventHeaders {

  private SignatureVersion signatureVersion;
  private String signature;
  private String signatureV3;
  private String timeoutMillis;
  private String requestTimestamp;

  public EventHeaders(@NonNull final Map<String, String> headers) {
    this.signatureVersion = SignatureVersion.get(headers.get(HubSpotHeaderNames.SIGNATURE_VERSION));
    this.signature = headers.get(HubSpotHeaderNames.SIGNATURE);
    this.signatureV3 = headers.get(HubSpotHeaderNames.SIGNATURE_V3);
    this.timeoutMillis = headers.get(HubSpotHeaderNames.TIMEOUT_MILLIS);
    this.requestTimestamp = headers.get(HubSpotHeaderNames.REQUEST_TIMESTAMP);
  }
}
