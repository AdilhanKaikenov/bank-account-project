package com.techbank.cqrs.core.handler;

import com.techbank.cqrs.core.domain.AggregateRoot;

/**
 * The purpose of the EventSourcingHandler is to retrieve all events for a given aggregate from
 * the Event Store and to invoke the replayEvents method on the AggregateRoot to recreate
 * the latest state of the aggregate.
 */
public interface EventSourcingHandler<T> {

  void save(AggregateRoot aggregate);

  T getById(String id);

}
