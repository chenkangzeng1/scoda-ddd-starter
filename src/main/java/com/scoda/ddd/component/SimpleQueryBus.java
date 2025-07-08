package com.scoda.ddd.component;

import com.scoda.ddd.model.base.error.BaseErrorCode;
import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.cqrs.BaseQuery;
import com.scoda.ddd.model.cqrs.Query;
import com.scoda.ddd.model.cqrs.handle.QueryBus;
import com.scoda.ddd.model.cqrs.handle.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of query bus, automatically registers and dispatches query handlers based on thread-safe Map, supports monitoring and exception handling.
 *
 * @author kangzeng.ckz
 * @since 2024/10/27
 **/
public class SimpleQueryBus implements QueryBus {
    private static final Logger logger = LoggerFactory.getLogger(SimpleQueryBus.class);
    private final Map<Class<? extends Query>, QueryHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    /**
     * Constructor for SimpleQueryBus.
     * @param queryHandlers the list of query handlers
     */
    public SimpleQueryBus(List<QueryHandler<?, ?>> queryHandlers) {
        logger.info("[SimpleQueryBus] Constructor called, handlers size: {}", queryHandlers.size());
        for (QueryHandler<?, ?> handler : queryHandlers) {
            Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(handler.getClass(), QueryHandler.class);
            if (typeArguments != null && typeArguments.length > 0) {
                Class<? extends Query> queryType = (Class<? extends Query>) typeArguments[0];
                handlers.put(queryType, handler);
                logger.info("[SimpleQueryBus] Registered handler for query: {}", queryType.getName());
            }
        }
        logger.info("[SimpleQueryBus] Registered {} query handlers", handlers.size());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send(BaseQuery<R> query) throws BaseException {
        if (query == null) {
            throw new NullPointerException("query must not be null");
        }
        logger.info("[SimpleQueryBus] send called for query: {}", query.getClass().getName());
        QueryHandler<Query<R>, R> handler = (QueryHandler<Query<R>, R>) handlers.get(query.getClass());
        if (handler == null) {
            throw new BaseException(BaseErrorCode.BUS_CLASS_NOT_FOUND_ERROR.getCode(), "No handler registered for query: " + query.getClass().getName());
        }
        return handler.handle(query);
    }
}