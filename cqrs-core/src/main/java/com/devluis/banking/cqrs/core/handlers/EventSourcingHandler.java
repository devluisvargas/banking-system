package com.devluis.banking.cqrs.core.handlers;

import com.devluis.banking.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
