package com.chat.line.service.helper;

import com.chat.line.model.enums.PromptType;

public class ChatHelper {

  private static final String SPACE_DELIMITER = " ";

  public static String getPromptType(String input) {
    int spaceIndex = input.indexOf(SPACE_DELIMITER);
    return input.substring(0, spaceIndex);
  }

  public static String getPrompt(PromptType promptType, String content) {
    return content.substring(promptType.getValue().length()).trim();
  }
}
