package com.example.hubspotintegrationapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Contact {
  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDateTime createdAt;
}
