package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.CreateContactRequest;
import com.example.hubspotintegrationapi.domain.CreateContactResponse;
import com.example.hubspotintegrationapi.usecases.contacts.CreateContact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

  private final CreateContact createContact;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public CreateContactResponse saveContact(@RequestBody CreateContactRequest request) {
    return createContact.execute(request);
  }
}
