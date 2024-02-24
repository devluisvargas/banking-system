package com.devluis.banking.account.cmd.application.command;

import com.devluis.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
