package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;

/**
 * The EventHandler is responsible to update the read database via
 * the AccountRepository after a new event was consumed from Kafka.
 */
public interface EventHandler {
  void on(AccountOpenedEvent event);
  void on(FundsDepositedEvent event);
  void on(FundsWithdrawnEvent event);
  void on(AccountClosedEvent event);
}
