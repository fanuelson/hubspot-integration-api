package com.example.hubspotintegrationapi.controller;

import com.example.hubspotintegrationapi.dto.CreateContactRequest;
import com.example.hubspotintegrationapi.dto.CreateContactResponse;
import com.example.hubspotintegrationapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

  private final ContactService contactService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public CreateContactResponse saveContact(@RequestBody CreateContactRequest request) {
    return contactService.createContact(request);
  }
}
