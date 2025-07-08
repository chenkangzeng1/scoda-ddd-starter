package com.scoda.ddd.component;

import com.scoda.ddd.model.cqrs.BaseQuery;
import com.scoda.ddd.model.cqrs.Command;
import com.scoda.ddd.model.domain.AbstractAggregateRoot;
import com.scoda.ddd.model.cqrs.handle.CommandBus;
import com.scoda.ddd.model.cqrs.handle.QueryBus;
import com.scoda.ddd.model.base.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.CompletableFuture;

/**
 * General CQRS facade, encapsulates synchronous/asynchronous/transactional dispatch of commands and queries, supports recursion protection and link tracing, can be injected and used directly by business.
 * 
 * @author kangzeng.ckz
 * @since 2024/10/27
 **/
@RequiredArgsConstructor
public class BusFacade {
    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final Executor cqrsAsyncExecutor;
    private Logger logger = LoggerFactory.getLogger(BusFacade.class);

    private static final int MAX_RECURSION_DEPTH = 10;
    private static final ThreadLocal<Integer> recursionDepth = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<StringBuilder> recursionTrace = ThreadLocal.withInitial(StringBuilder::new);

    /**
     * Sends a command synchronously.
     * @param command the command to send
     * @param <C> the command type
     * @param <R> the result type
     * @return the result of command execution
     * @throws BaseException if command execution fails
     */
    public <C extends Command<R>, R extends AbstractAggregateRoot> R sendCommand(C command) throws BaseException {
        if (command == null) {
            throw new NullPointerException("command must not be null");
        }
        int depth = recursionDepth.get();
        if (depth > MAX_RECURSION_DEPTH) {
            String trace = recursionTrace.get().toString();
            logger.error("[BusFacade] Command recursion too deep! Trace: {}", trace);
            throw new IllegalStateException("Command recursion too deep! Trace: " + trace);
        }
        recursionDepth.set(depth + 1);
        recursionTrace.get().append("->").append(command.getClass().getSimpleName());
        try {
            return commandBus.send(command);
        } finally {
            recursionDepth.set(depth);
            StringBuilder trace = recursionTrace.get();
            int idx = trace.lastIndexOf("->");
            if (idx >= 0) trace.delete(idx, trace.length());
        }
    }

    /**
     * Sends a command via message queue (not implemented).
     * @param command the command to send
     * @param <C> the command type
     * @param <R> the result type
     * @return the result of command execution
     * @throws BaseException if command execution fails
     */
    public <C extends Command<R>, R extends AbstractAggregateRoot> R sendMqCommand(C command) throws BaseException {
        throw new UnsupportedOperationException("sendMqCommand feature is not yet implemented, do not call!");
    }

    /**
     * Sends a command asynchronously.
     * @param command the command to send
     * @param <C> the command type
     * @param <R> the result type
     * @return a CompletableFuture containing the result of command execution
     */
    public <C extends Command<R>, R extends AbstractAggregateRoot> CompletableFuture<R> sendAsyncCommand(C command) {
        if (command == null) {
            throw new NullPointerException("command must not be null");
        }
        int depth = recursionDepth.get();
        if (depth > MAX_RECURSION_DEPTH) {
            String trace = recursionTrace.get().toString();
            logger.error("[BusFacade] Command recursion too deep! Trace: {}", trace);
            throw new IllegalStateException("Command recursion too deep! Trace: " + trace);
        }
        recursionDepth.set(depth + 1);
        recursionTrace.get().append("->").append(command.getClass().getSimpleName());
        try {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return commandBus.send(command);
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
            }, cqrsAsyncExecutor);
        } finally {
            recursionDepth.set(depth);
            StringBuilder trace = recursionTrace.get();
            int idx = trace.lastIndexOf("->");
            if (idx >= 0) trace.delete(idx, trace.length());
        }
    }

    /**
     * Sends a command in a transaction.
     * @param command the command to send
     * @param <C> the command type
     * @param <R> the result type
     * @return the result of command execution
     * @throws BaseException if command execution fails
     */
    public <C extends Command<R>, R extends AbstractAggregateRoot> R sendTransactCommand(C command) throws BaseException {
        if (command == null) {
            throw new NullPointerException("command must not be null");
        }
        int depth = recursionDepth.get();
        if (depth > MAX_RECURSION_DEPTH) {
            String trace = recursionTrace.get().toString();
            logger.error("[BusFacade] Command recursion too deep! Trace: {}", trace);
            throw new IllegalStateException("Command recursion too deep! Trace: " + trace);
        }
        recursionDepth.set(depth + 1);
        recursionTrace.get().append("->").append(command.getClass().getSimpleName());
        try {
            return commandBus.send(command);
        } finally {
            recursionDepth.set(depth);
            StringBuilder trace = recursionTrace.get();
            int idx = trace.lastIndexOf("->");
            if (idx >= 0) trace.delete(idx, trace.length());
        }
    }

    /**
     * Sends a query.
     * @param query the query to send
     * @param <Q> the query type
     * @param <R> the result type
     * @return the result of query execution
     * @throws BaseException if query execution fails
     */
    public <Q extends BaseQuery<R>, R> R sendQuery(Q query) throws BaseException {
        if (query == null) {
            throw new NullPointerException("query must not be null");
        }
        return queryBus.send(query);
    }
} 