package com.chat.line.service.api;

import com.chat.line.model.entity.ChatRequest;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.model.entity.ImageData;
import com.chat.line.model.entity.ImageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChatGptService {

  ChatMessage chat(ChatRequest request) throws JsonProcessingException;

  ImageData generateImage(ImageRequest request) throws JsonProcessingException;
}
