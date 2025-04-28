package com.example.hubspotintegrationapi.domain.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class HubSpotRateLimitContext {
    private final Long intervalMilliseconds;
    private final Long max;
    private final Long remaining;
}
