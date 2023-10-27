package com.chat.line.service.api.impl;

import com.chat.line.model.entity.ChatCompletionsRequest;
import com.chat.line.model.entity.ChatCompletionsResponse;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.service.api.ChatGptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

  @Override
  public ChatMessage chat(ChatCompletionsRequest request) {
    HttpHeaders headers = this.constructHeaders();

    HttpEntity<ChatCompletionsRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<ChatCompletionsResponse> response = restTemplate
        .exchange(this.getCompletionsUrl(), HttpMethod.POST, entity, ChatCompletionsResponse.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      return this.getMessageFromResponse(response);
    } else {
      throw new RuntimeException("Error communicating with the GPT API."); // Handle as needed
    }
  }

  private ChatMessage getMessageFromResponse(ResponseEntity<ChatCompletionsResponse> response) {
    return response.getBody().getChoices().get(0).getMessages();
  }

  private HttpHeaders constructHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + this.apiKey);
    headers.add("Content-Type", "application/json");
    return headers;
  }

  private String getCompletionsUrl() {
    return this.apiUrl + this.completionsContextPath;
  }
}
