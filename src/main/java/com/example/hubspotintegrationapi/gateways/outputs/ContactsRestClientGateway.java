package com.example.hubspotintegrationapi.gateways.outputs;

import com.example.hubspotintegrationapi.domain.contacts.Contact;

import java.util.Optional;

public interface ContactsRestClientGateway {
  Optional<Contact> create(Contact input);
  Optional<Contact> findByEmail(String email);
}
