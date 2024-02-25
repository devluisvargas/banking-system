package com.devluis.banking.account.query.infrastructure.consumer;

import com.devluis.banking.account.comman.events.AccountClosedEvent;
import com.devluis.banking.account.comman.events.AccountOpenedEvent;
import com.devluis.banking.account.comman.events.FundsDepositedEvent;
import com.devluis.banking.account.comman.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);

    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);

    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);

    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
