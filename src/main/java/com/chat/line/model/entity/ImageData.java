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
public class ImageData implements Serializable {
  @Serial
  private static final long serialVersionUID = -3135378427038786047L;

  private String url;
}
