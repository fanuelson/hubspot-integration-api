package com.example.hubspotintegrationapi.controller;

import com.example.hubspotintegrationapi.dto.ContactRequest;
import com.example.hubspotintegrationapi.dto.ContactResponse;
import com.example.hubspotintegrationapi.service.HubspotContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

  private final HubspotContactService contactService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ContactResponse saveContact(@RequestBody ContactRequest request) {
    return contactService.createContact(request);
  }
}
