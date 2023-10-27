package com.chat.line.service.api.impl;

import com.chat.line.model.constant.RoleNames;
import com.chat.line.model.entity.ChatMessage;
import com.chat.line.service.api.BotService;
import com.chat.line.service.api.ChatGptService;
import com.chat.line.service.helper.ChatGptHelper;
import com.linecorp.bot.client.base.BlobContent;
import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.ReplyMessageResponse;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
  public void handleTextContent(String replyToken, MessageEvent event, TextMessageContent content) {
    ChatMessage userMessage = ChatGptHelper.constructMessage(RoleNames.USER, content.text());
    ChatMessage message = this.chatGptService
        .chat(ChatGptHelper.constructCompletionsRequest("gpt-4", singletonList(userMessage)));

    this.reply(replyToken, new TextMessage(message.getContent()));
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
