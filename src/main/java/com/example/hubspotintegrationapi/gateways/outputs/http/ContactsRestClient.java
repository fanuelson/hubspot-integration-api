package com.example.hubspotintegrationapi.gateways.outputs.http;

public interface ContactsRestClient<I, O> {
  O create(I input);
}
