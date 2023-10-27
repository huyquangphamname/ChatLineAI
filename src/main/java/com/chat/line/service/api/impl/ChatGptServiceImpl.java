package com.chat.line.service.api.impl;

import com.chat.line.model.entity.ChatCompletionsRequest;
import com.chat.line.model.entity.ChatCompletionsResponse;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.service.api.ChatGptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGptServiceImpl implements ChatGptService {

  @Value("${gpt.api.url}")
  private String apiUrl;

  @Value("${gpt.api.completions-context-path}")
  private String completionsContextPath;

  @Value("${gpt.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public ChatMessage chat(ChatCompletionsRequest request) throws JsonProcessingException {
    HttpHeaders headers = this.constructHeaders();

    HttpEntity<ChatCompletionsRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<String> response =
        restTemplate.exchange(this.getCompletionsUrl(), HttpMethod.POST, entity, String.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      return this.getMessageFromResponse(response);
    } else {
      throw new RuntimeException("Error communicating with the GPT API."); // Handle as needed
    }
  }

  private ChatMessage getMessageFromResponse(ResponseEntity<String> response)
      throws JsonProcessingException {
    ChatCompletionsResponse chatCompletionsResponse =
        objectMapper.readValue(response.getBody(), ChatCompletionsResponse.class);

    return chatCompletionsResponse.getChoices().get(0).getMessage();
  }

  private HttpHeaders constructHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(this.apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  private String getCompletionsUrl() {
    return this.apiUrl + this.completionsContextPath;
  }
}
