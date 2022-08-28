package com.techbank.cqrs.core.command;

import com.techbank.cqrs.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseCommand extends Message {

  public BaseCommand(String id) {
    super(id);
  }
}
