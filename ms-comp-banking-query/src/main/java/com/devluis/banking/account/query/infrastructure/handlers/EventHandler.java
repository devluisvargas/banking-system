package com.devluis.banking.account.query.infrastructure.handlers;

import com.devluis.banking.account.comman.events.AccountClosedEvent;
import com.devluis.banking.account.comman.events.AccountOpenedEvent;
import com.devluis.banking.account.comman.events.FundsDepositedEvent;
import com.devluis.banking.account.comman.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
