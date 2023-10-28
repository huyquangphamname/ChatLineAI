package com.chat.line.service.helper;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ValidatorHelper {

  public static boolean validMessageContent(String content) {
    return StringUtils.isNotBlank(content) && content.startsWith("!");
  }

  public static boolean validateSignature(String channelSecret, String payload, String headerSignature) {
    try {
      SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(key);
      byte[] source = payload.getBytes(StandardCharsets.UTF_8);
      String computedHmac = Base64.getEncoder().encodeToString(mac.doFinal(source));
      return headerSignature.equals(computedHmac);
    } catch (Exception e) {
      return false; // Handle exceptions properly in production
    }
  }

}
