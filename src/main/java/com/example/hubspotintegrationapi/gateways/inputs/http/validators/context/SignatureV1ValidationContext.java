package com.example.hubspotintegrationapi.gateways.inputs.http.validators.context;

import org.springframework.lang.NonNull;

public record SignatureV1ValidationContext(@NonNull String signature, @NonNull String rawBody) {}
