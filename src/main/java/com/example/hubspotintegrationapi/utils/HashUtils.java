package com.example.hubspotintegrationapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.lang.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtils {

  public static String sha256Hex(@NonNull final String data) {
    return DigestUtils.sha256Hex(data);
  }
}
