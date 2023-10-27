package com.chat.line.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.bot.client.base.BlobContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

import java.util.function.Consumer;

public interface BotService {

  void handleTextContent(String replyToken, MessageEvent event, TextMessageContent content) throws JsonProcessingException;

  void handleHeavyContent(String replyToken, String messageId,
      Consumer<BlobContent> messageConsumer);
}
