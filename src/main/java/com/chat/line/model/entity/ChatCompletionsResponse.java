package com.chat.line.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionsResponse implements Serializable {
  @Serial
  private static final long serialVersionUID = -8085694150831820608L;

  private List<Choices> choices;
}
