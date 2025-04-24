package com.example.hubspotintegrationapi.domain.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
@Getter
public class OAuth2CallbackContext {

  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final String code;
  private final String error;

  public boolean hasError() {
    return StringUtils.isNotBlank(error);
  }

  public String getBaseUrl() {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
  }
}
