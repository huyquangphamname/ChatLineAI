package com.chat.line.service.api.impl;

import com.chat.line.model.entity.ChatRequest;
import com.chat.line.model.entity.ChatResponse;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.model.entity.ImageData;
import com.chat.line.model.entity.ImageRequest;
import com.chat.line.model.entity.ImageResponse;
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

  @Value("${gpt.api.images-context-path}")
  private String imagesContextPath;

  @Value("${gpt.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public ChatMessage chat(ChatRequest request) throws JsonProcessingException {
    HttpHeaders headers = this.constructHeaders();

    HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<String> response =
        restTemplate.exchange(this.getCompletionsUrl(), HttpMethod.POST, entity, String.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      return this.getMessageFromResponse(response);
    } else {
      throw new RuntimeException("Error communicating with the GPT API."); // Handle as needed
    }
  }

  @Override
  public ImageData generateImage(ImageRequest request) throws JsonProcessingException {
    HttpHeaders headers = this.constructHeaders();

    HttpEntity<ImageRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<String> response =
        restTemplate.exchange(this.getImagesUrl(), HttpMethod.POST, entity, String.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      return this.getImageFromResponse(response);
    } else {
      throw new RuntimeException("Error communicating with the GPT API."); // Handle as needed
    }
  }

  private ImageData getImageFromResponse(ResponseEntity<String> response)
      throws JsonProcessingException {
    ImageResponse imageResponse = objectMapper.readValue(response.getBody(), ImageResponse.class);

    return imageResponse.getData().get(0);
  }

  private ChatMessage getMessageFromResponse(ResponseEntity<String> response)
      throws JsonProcessingException {
    ChatResponse chatResponse = objectMapper.readValue(response.getBody(), ChatResponse.class);

    return chatResponse.getChoices().get(0).getMessage();
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

  private String getImagesUrl() {
    return this.apiUrl + this.imagesContextPath;
  }
}
