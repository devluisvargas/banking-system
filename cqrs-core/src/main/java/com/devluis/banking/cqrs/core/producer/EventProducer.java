package com.devluis.banking.cqrs.core.producer;

import com.devluis.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
