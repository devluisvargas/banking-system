package com.devluis.banking.account.query.application.controllers;

import com.devluis.banking.account.query.application.dto.AccountLookupResponse;
import com.devluis.banking.account.query.application.dto.EqualityType;
import com.devluis.banking.account.query.application.queries.FindAccountByHolderQuery;
import com.devluis.banking.account.query.application.queries.FindAccountByIdQuery;
import com.devluis.banking.account.query.application.queries.FindAccountWithBalanceQuery;
import com.devluis.banking.account.query.application.queries.FindAllAccountsQuery;
import com.devluis.banking.account.query.domain.BankAccount;
import com.devluis.banking.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/bankAccountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            return getAccountLookupResponseResponseEntity(accounts);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value="id") String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            return getAccountLookupResponseResponseEntity(accounts);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value="accountHolder") String accountHolder){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            return getAccountLookupResponseResponseEntity(accounts);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
            @PathVariable(value="equalityType") EqualityType equalityType,
            @PathVariable(value="balance") double balance
    )
    {
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance, equalityType));
            return getAccountLookupResponseResponseEntity(accounts);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<AccountLookupResponse> getAccountLookupResponseResponseEntity(List<BankAccount> accounts) {
        if(accounts == null || accounts.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = AccountLookupResponse.builder()
                .accounts(accounts)
                .message(MessageFormat.format("Se realizo la consulta con exito", null))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
