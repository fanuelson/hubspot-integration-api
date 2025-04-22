package com.example.hubspotintegrationapi.usecases.contacts;

import com.example.hubspotintegrationapi.domain.CreateContactRequest;
import com.example.hubspotintegrationapi.domain.CreateContactResponse;
import com.example.hubspotintegrationapi.gateways.outputs.http.ContactsRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateContact {

  private final ContactsRestClient<CreateContactRequest, CreateContactResponse> contactsRestClient;

  public CreateContactResponse execute(final CreateContactRequest createContactRequest) {
    return contactsRestClient.create(createContactRequest);
  }
}
