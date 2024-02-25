package com.devluis.banking.account.cmd.domain;

import com.devluis.banking.account.cmd.application.command.OpenAccountCommand;
import com.devluis.banking.account.comman.events.AccountClosedEvent;
import com.devluis.banking.account.comman.events.AccountOpenedEvent;
import com.devluis.banking.account.comman.events.FundsDepositedEvent;
import com.devluis.banking.account.comman.events.FundsWithdrawnEvent;
import com.devluis.banking.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;

    @Getter
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalancer())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Los fondos no pueden ser depositados en esta cuenta");
        }

        if(amount <= 0){
            throw new IllegalStateException("El desposito de dinero no puede ser menos que cero");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount){
        if (!this.active) {
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if (!this.active) {
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
