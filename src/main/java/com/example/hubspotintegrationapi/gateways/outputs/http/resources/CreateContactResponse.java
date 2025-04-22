package com.example.hubspotintegrationapi.gateways.outputs.http.resources;

import com.example.hubspotintegrationapi.domain.Properties;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateContactResponse extends Properties<Map> {
  private String id;
  private LocalDateTime createdAt;
}
