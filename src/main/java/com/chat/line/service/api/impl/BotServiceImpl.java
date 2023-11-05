package com.chat.line.service.api.impl;

import com.chat.line.model.constant.ModelNames;
import com.chat.line.model.constant.RoleNames;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.model.entity.ImageData;
import com.chat.line.model.entity.ImageRequest;
// import com.chat.line.model.entity.ImageResponse;
import com.chat.line.model.enums.PromptType;
import com.chat.line.service.api.BotService;
import com.chat.line.service.api.ChatGptService;
import com.chat.line.service.helper.ChatGptHelper;
import com.chat.line.service.helper.ChatHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.bot.client.base.BlobContent;
import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.ImageMessage;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.ReplyMessageResponse;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
// import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

  private final MessagingApiClient messagingApiClient;
  private final ChatGptService chatGptService;

  @Override
  public void handleTextContent(String replyToken, MessageEvent event, TextMessageContent content)
      throws JsonProcessingException, URISyntaxException {
    PromptType promptType = PromptType.getPromptTypeOrDefault(content.text());
    String prompt = ChatHelper.getPrompt(promptType, content.text());

    this.handleTextContent(replyToken, promptType, prompt);
  }

  private void handleTextContent(String replyToken, PromptType promptType, String prompt)
      throws JsonProcessingException, URISyntaxException {
    if (PromptType.GPT_4.equals(promptType)) {
      this.handleTextContentAndReplyText(replyToken, ModelNames.GPT_4, prompt);
    } else if (PromptType.CREATE_IMAGE.equals(promptType)) {
      this.handleTextContentAndReplyImage(replyToken, prompt);
    } else {
      this.handleTextContentAndReplyText(replyToken, ModelNames.DEFAULT, prompt);
    }
  }
  
  private void handleTextContentAndReplyText(String replyToken, String model, String content)
      throws JsonProcessingException {
    ChatMessage userMessage = ChatGptHelper.constructMessage(RoleNames.USER, content);
    ChatMessage message =
        this.chatGptService.chat(ChatGptHelper.constructCompletionsRequest(model, userMessage));

    this.reply(replyToken, new TextMessage(message.getContent()));
  }

  private void handleTextContentAndReplyImage(String replyToken, String content)
      throws JsonProcessingException, URISyntaxException {
    ImageRequest imageRequest = ChatGptHelper.constructImageRequest(content);
    ImageData imageData = this.chatGptService.generateImage(imageRequest);

    URI uri = new URI(imageData.getUrl());
    this.reply(replyToken, new ImageMessage(uri, uri));
  }

  @Override
  public void handleHeavyContent(String replyToken, String messageId,
      Consumer<BlobContent> messageConsumer) {

  }

  private void reply(String replyToken, Message message) {
    Objects.requireNonNull(replyToken, "replyToken");
    Objects.requireNonNull(message, "message");
    reply(replyToken, singletonList(message));
  }

  private void reply(String replyToken, List<Message> messages) {
    Objects.requireNonNull(replyToken, "replyToken");
    Objects.requireNonNull(messages, "messages");
    reply(replyToken, messages, false);
  }

  private void reply(String replyToken, List<Message> messages, boolean notificationDisabled) {
    try {
      Objects.requireNonNull(replyToken, "replyToken");
      Objects.requireNonNull(messages, "messages");
      Result<ReplyMessageResponse> apiResponse = messagingApiClient
          .replyMessage(new ReplyMessageRequest(replyToken, messages, notificationDisabled)).get();
      log.info("Sent messages: {}", apiResponse);
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
