package com.devluis.banking.account.cmd.application.controllers;

import com.devluis.banking.account.cmd.application.command.OpenAccountCommand;
import com.devluis.banking.account.cmd.application.dto.OpenAccountResponse;
import com.devluis.banking.account.comman.dto.BaseResponse;
import com.devluis.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;


    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La cuenta del banco se ha creado exitosamente", id), HttpStatus.CREATED);
        }catch (IllegalStateException e){
            logger.log(Level.WARNING, MessageFormat.format("No se pudo generar la cuenta de banco - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErroMessage = MessageFormat.format("Errores mientras procesaba el request - {0}", id);
            logger.log(Level.SEVERE, safeErroMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErroMessage, id),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
