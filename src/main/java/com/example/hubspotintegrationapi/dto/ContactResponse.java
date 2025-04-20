package com.example.hubspotintegrationapi.dto;

import java.util.Map;

public record ContactResponse(String id, Map<String, Object> properties) {}
