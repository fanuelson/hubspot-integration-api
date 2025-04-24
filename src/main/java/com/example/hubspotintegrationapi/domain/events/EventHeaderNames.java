package com.example.hubspotintegrationapi.domain.events;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHeaderNames {
    public static final String SIGNATURE_VERSION = "X-HubSpot-Signature-Version";
    public static final String SIGNATURE = "X-HubSpot-Signature";
    public static final String SIGNATURE_V3 = "X-HubSpot-Signature-v3";
    public static final String TIMEOUT_MILLIS = "X-HubSpot-Timeout-Millis";
    public static final String REQUEST_TIMESTAMP = "X-HubSpot-Request-Timestamp";
}

