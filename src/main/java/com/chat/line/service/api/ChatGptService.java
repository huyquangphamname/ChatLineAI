package com.chat.line.service.api;

import com.chat.line.model.entity.ChatCompletionsRequest;
import com.chat.line.model.entity.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChatGptService {

  ChatMessage chat(ChatCompletionsRequest request) throws JsonProcessingException;
}
