package com.example.hubspotintegrationapi.mock;

import com.example.hubspotintegrationapi.domain.Contact;

public class ContactMock {
  public static Contact createContactValidMock() {
    return Contact.builder().firstName("John").lastName("Cena").email("john@gmail.com").build();
  }
}
