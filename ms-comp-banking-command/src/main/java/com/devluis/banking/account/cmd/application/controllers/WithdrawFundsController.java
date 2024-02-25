package com.devluis.banking.account.cmd.application.controllers;

import com.devluis.banking.account.cmd.application.command.WithdrawFundsCommand;
import com.devluis.banking.account.cmd.application.dto.OpenAccountResponse;
import com.devluis.banking.account.comman.dto.BaseResponse;
import com.devluis.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.devluis.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id, @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("El retiro de dinero fue exitoso"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("El cliente envio un request con errores - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErroMessage = MessageFormat.format("Errores mientras procesaba el request - {0}", id);
            logger.log(Level.SEVERE, safeErroMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErroMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
