package com.devluis.banking.cqrs.core.infrastructure;

import com.devluis.banking.cqrs.core.domain.BaseEntity;
import com.devluis.banking.cqrs.core.query.BaseQuery;
import com.devluis.banking.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
