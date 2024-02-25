package com.devluis.banking.account.query.domain;

import com.devluis.banking.account.comman.dto.AccountType;
import com.devluis.banking.cqrs.core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {
    @Id
    private String   id;
    private String accountHolder;
    private Date creationDate;
    private AccountType accountType;
    private double balance;
}
