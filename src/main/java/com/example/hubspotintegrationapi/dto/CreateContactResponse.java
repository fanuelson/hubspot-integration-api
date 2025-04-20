package com.example.hubspotintegrationapi.dto;

import java.util.Map;

public record CreateContactResponse(String id, Map<String, Object> properties) {}
