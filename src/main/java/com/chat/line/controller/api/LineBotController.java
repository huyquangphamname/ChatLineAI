package com.chat.line.controller.api;

import com.chat.line.service.api.BotService;
import com.chat.line.service.helper.ValidatorHelper;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@LineMessageHandler
@RequiredArgsConstructor
public class LineBotController {

  private final BotService botService;

  @EventMapping
  public void handleTextMessageEvent(MessageEvent event, TextMessageContent textMessageContent)
      throws Exception {
    if (ValidatorHelper.validMessageContent(textMessageContent.text())) {
      this.botService.handleTextContent(event.replyToken(), event, textMessageContent);
    }
    log.info("#handleTextMessageEvent called with event: {}, and content: {}", event,
        textMessageContent);
  }

  @EventMapping
  public void handleImageMessageEvent(MessageEvent event, ImageMessageContent imageMessageContent)
      throws IOException {
    this.botService.handleHeavyContent(event.replyToken(), imageMessageContent.id(),
        responseBody -> {
        });
    log.info("#handleImageMessageEvent called with event: {}, and content: {}", event,
        imageMessageContent);
  }
}
