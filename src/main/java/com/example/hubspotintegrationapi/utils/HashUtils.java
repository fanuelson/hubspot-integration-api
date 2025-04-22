package com.example.hubspotintegrationapi.utils;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtils {

  public static String generateHmacSHA256Base64(
      @NonNull final String data, @NonNull final String secret) {
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
      sha256_HMAC.init(secretKey);
      byte[] hashBytes = sha256_HMAC.doFinal(data.getBytes());
      return Base64.getEncoder().encodeToString(hashBytes);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao gerar HMAC SHA256", e);
    }
  }
}
