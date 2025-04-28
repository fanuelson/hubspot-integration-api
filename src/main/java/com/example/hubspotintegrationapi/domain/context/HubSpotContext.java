package com.example.hubspotintegrationapi.domain.context;

import lombok.*;

@Getter
@With
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class HubSpotContext<T> {
  private final String correlationId;
  private final HubSpotRateLimitContext rateLimitContext;

  @Setter
  private T object;
}
