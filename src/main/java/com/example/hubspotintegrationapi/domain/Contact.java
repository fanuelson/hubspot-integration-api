package com.example.hubspotintegrationapi.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
public class Contact {
  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDateTime createdAt;
}
