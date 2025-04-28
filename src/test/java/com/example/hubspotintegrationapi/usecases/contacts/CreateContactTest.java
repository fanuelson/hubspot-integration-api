package com.example.hubspotintegrationapi.usecases.contacts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.hubspotintegrationapi.domain.contacts.Contact;
import com.example.hubspotintegrationapi.domain.validation.ErrorMessage;
import com.example.hubspotintegrationapi.domain.validation.ValidationError;
import com.example.hubspotintegrationapi.exceptions.BusinessValidationException;
import com.example.hubspotintegrationapi.gateways.outputs.ContactsClientGateway;
import com.example.hubspotintegrationapi.mocks.ContactMocks;
import com.example.hubspotintegrationapi.usecases.contacts.validators.ContactEmailExistsValidator;
import java.util.Optional;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class CreateContactTest {

  @InjectMocks private CreateContact createContact;

  @Mock private ContactsClientGateway contactsClientGateway;

  @Mock private ContactEmailExistsValidator contactEmailExistsValidator;

  @Test
  void shouldThrowErrorIfRestClientNotAuthorized() {
    val createContactInput = ContactMocks.createContactValidMock();

    when(contactEmailExistsValidator.validate(any())).thenReturn(Optional.empty());
    when(contactsClientGateway.create(any(Contact.class)))
        .thenThrow(ClientAuthorizationRequiredException.class);

    assertThrows(ClientAuthorizationRequiredException.class, () -> createContact.execute(createContactInput));
  }

  @Test
  void shouldThrowErrorIfInvalidEmailAddress() {
    val invalidEmail = "invalid";
    val createContactInput = ContactMocks.createContactValidMock().withEmail(invalidEmail);

    when(contactEmailExistsValidator.validate(any())).thenReturn(Optional.empty());
    when(contactsClientGateway.create(any(Contact.class)))
            .thenThrow(HttpClientErrorException.BadRequest.class);

    assertThrows(HttpClientErrorException.BadRequest.class, () -> createContact.execute(createContactInput));
  }

  @Test
  void shouldThrowErrorIfContactEmailAlreadyExists() {
    val existingEmail = "test@gmail.com";
    val createContactInput = ContactMocks.createContactValidMock().withEmail(existingEmail);

    when(contactEmailExistsValidator.validate(existingEmail))
        .thenReturn(
            Optional.of(new ValidationError(ErrorMessage.CONTACT_EMAIL_ALREADY_EXISTS, existingEmail)));

    assertThrows(BusinessValidationException.class, () -> createContact.execute(createContactInput));
  }

  @Test
  void shouldCreateContact() {
    val createContactInput = ContactMocks.createContactValidMock();
    val expectedCreateContactOutput = createContactInput.withId("1");

    when(contactEmailExistsValidator.validate(any())).thenReturn(Optional.empty());
    when(contactsClientGateway.create(any(Contact.class)))
        .thenReturn(Optional.of(createContactInput.withId("1")));

    val contactOutput = createContact.execute(createContactInput);

    assertNotNull(contactOutput);
    Assertions.assertThat(contactOutput).usingRecursiveComparison().isEqualTo(expectedCreateContactOutput);
  }
}
