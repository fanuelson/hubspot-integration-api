package com.example.hubspotintegrationapi.gateways.outputs.http;

import com.example.hubspotintegrationapi.domain.Contact;

import java.util.Optional;

public interface ContactsRestClientGateway {
  Optional<Contact> create(Contact input);
}
