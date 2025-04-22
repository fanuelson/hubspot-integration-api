package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.gateways.inputs.http.resources.request.CreateContactRequest;
import com.example.hubspotintegrationapi.gateways.inputs.http.resources.response.CreateContactResponse;
import com.example.hubspotintegrationapi.usecases.contacts.CreateContact;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

  private final CreateContact createContact;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public CreateContactResponse create(@RequestBody CreateContactRequest request) {
    val contact = createContact.execute(request.toDomain());
    return new CreateContactResponse(contact);
  }
}
