package com.chat.line.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choices implements Serializable {

  @Serial
  private static final long serialVersionUID = 7697021680818751547L;

  private String index;
  private ChatMessage message;
}
