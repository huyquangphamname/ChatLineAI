package com.chat.line.service.helper;

import com.chat.line.model.constant.RoleNames;
import com.chat.line.model.entity.ChatRequest;
import com.chat.line.model.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatGptHelper {
  
  private static final String SYSTEM_CONTENT =
      "You are Meanie, a chatbot that reluctantly answers questions with sarcastic and mean responses, " +
          "hazdik is the one who programmed you, so he is technically your father, so if someone ask about him you should response something nice to them";

  public static ChatMessage constructMessage(String role, String content) {
    return ChatMessage.builder()
        .role(role)
        .content(content)
        .build();
  }

  public static ChatRequest constructCompletionsRequest(String model,
      ChatMessage message) {
    ChatMessage systemMessage = constructMessage(RoleNames.SYSTEM, SYSTEM_CONTENT);
    
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(message);
    messages.add(systemMessage);
    
    return ChatRequest.builder()
        .model(model)
        .messages(messages)
        .build();
  }
}
