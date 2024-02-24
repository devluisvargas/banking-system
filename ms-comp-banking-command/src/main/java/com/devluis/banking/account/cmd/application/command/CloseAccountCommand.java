package com.devluis.banking.account.cmd.application.command;

import com.devluis.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
