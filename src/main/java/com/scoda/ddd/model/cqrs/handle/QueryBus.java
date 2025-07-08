package com.scoda.ddd.model.cqrs.handle;


import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.cqrs.BaseQuery;

/**
 * Query bus interface, responsible for dispatching and executing Queries, decoupling queries and handlers, supporting query processing flow under DDD/CQRS architecture.
 *
 * @author kangzeng.ckz
 * @since 2024/10/27
 **/
public interface QueryBus {
    /**
     * Sends a query for execution.
     * @param query the query to send
     * @param <R> the result type
     * @return the result of query execution
     * @throws BaseException if query execution fails
     */
    <R> R send(BaseQuery<R> query) throws BaseException;
} 