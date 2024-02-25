package com.devluis.banking.cqrs.core.query;

import com.devluis.banking.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery>{
    List<BaseEntity> handle(T query);
}
