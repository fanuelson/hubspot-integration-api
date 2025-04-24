package com.example.hubspotintegrationapi.gateways.inputs.http.controllers;

import com.example.hubspotintegrationapi.domain.oauth2.OAuth2CallbackContext;
import com.example.hubspotintegrationapi.usecases.oauth2.GenerateOAuth2AuthorizationUrl;
import com.example.hubspotintegrationapi.usecases.oauth2.OAuth2AuthorizeClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

  private final GenerateOAuth2AuthorizationUrl generateOAuth2AuthorizationUrl;
  private final OAuth2AuthorizeClient oAuth2AuthorizeClient;

  @GetMapping("/authorize")
  @ResponseStatus(HttpStatus.OK)
  public String authorize(final HttpServletRequest request) {
    return generateOAuth2AuthorizationUrl.execute(request);
  }

  @GetMapping("/callback")
  @ResponseStatus(HttpStatus.OK)
  public void callback(
      @RequestParam(required = false) final String code,
      @RequestParam(required = false) final String error,
      final HttpServletRequest request,
      final HttpServletResponse response)
      throws IOException {

    val oauth2CallbackContext = new OAuth2CallbackContext(request, response, code, error);
    oAuth2AuthorizeClient.execute(oauth2CallbackContext);
    response.sendRedirect(request.getContextPath() + "/swagger-ui/index.html");
  }
}
