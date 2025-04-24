package com.example.hubspotintegrationapi.domain.events;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHeaderNames {
    public static final String SIGNATURE_VERSION = "X-Hubspot-Signature-Version";
    public static final String SIGNATURE = "X-Hubspot-Signature";
    public static final String SIGNATURE_V3 = "X-Hubspot-Signature-v3";
    public static final String TIMEOUT_MILLIS = "X-Hubspot-Timeout-Millis";
    public static final String REQUEST_TIMESTAMP = "X-Hubspot-Request-Timestamp";
}

