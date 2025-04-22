package com.example.hubspotintegrationapi.domain;

import java.util.Map;

public record CreateContactResponse(String id, Map<String, Object> properties) {}
