package com.chat.line.service.helper;

import com.chat.line.model.entity.ChatCompletionsRequest;
import com.chat.line.model.entity.ChatMessage;

import java.util.List;

public class ChatGptHelper {

  public static ChatMessage constructMessage(String role, String content) {
    return ChatMessage.builder()
        .role(role)
        .content(content)
        .build();
  }

  public static ChatCompletionsRequest constructCompletionsRequest(String model,
      List<ChatMessage> messages) {
    return ChatCompletionsRequest.builder()
        .model(model)
        .messages(messages)
        .build();
  }
}
