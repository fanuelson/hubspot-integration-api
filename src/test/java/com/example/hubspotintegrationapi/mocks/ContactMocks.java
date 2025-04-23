package com.example.hubspotintegrationapi.mocks;

import com.example.hubspotintegrationapi.domain.contacts.Contact;

public class ContactMocks {
  public static Contact createContactValidMock() {
    return Contact.builder().firstName("John").lastName("Cena").email("john@gmail.com").build();
  }
}
