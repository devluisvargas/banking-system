package com.devluis.banking.account.query.application.queries;

import com.devluis.banking.account.query.application.dto.EqualityType;
import com.devluis.banking.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private double balance;
    private EqualityType equalityType;
}
