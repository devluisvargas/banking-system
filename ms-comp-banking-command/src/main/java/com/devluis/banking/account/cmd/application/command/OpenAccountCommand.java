package com.devluis.banking.account.cmd.application.command;

import com.devluis.banking.account.comman.dto.AccountType;
import com.devluis.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalancer;
}
