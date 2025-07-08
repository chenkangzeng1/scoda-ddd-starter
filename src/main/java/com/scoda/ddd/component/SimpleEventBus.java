package com.scoda.ddd.component;

import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.cqrs.handle.EventBus;
import com.scoda.ddd.model.cqrs.handle.EventHandler;
import com.scoda.ddd.model.domain.DomainEvent;
import org.springframework.core.GenericTypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default implementation of event bus, supports automatic registration of event handlers, thread-safe subscription and publishing, adapted for domain event-driven architecture.
 *
 * @author kangzeng.ckz
 * @since 2024/10/29
 **/
public class SimpleEventBus implements EventBus {
    private static final Logger logger = LoggerFactory.getLogger(SimpleEventBus.class);
    private final Map<Class<? extends DomainEvent>, List<EventHandler>> handlers = new ConcurrentHashMap<>();

    /**
     * Constructor for SimpleEventBus.
     * @param eventHandlers the list of event handlers
     */
    public SimpleEventBus(List<EventHandler<? extends DomainEvent>> eventHandlers) {
        logger.info("[SimpleEventBus] Constructor called, handlers size: {}", eventHandlers.size());
        for (EventHandler<? extends DomainEvent> handler : eventHandlers) {
            Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(
                    handler.getClass(),
                    EventHandler.class
            );
            if (typeArguments != null && typeArguments.length > 0) {
                Class<? extends DomainEvent> eventType = (Class<? extends DomainEvent>) typeArguments[0];
                subscribe(eventType, handler);
                logger.info("[SimpleEventBus] Registered handler for event: {}", eventType.getName());
            }
        }
        logger.info("[SimpleEventBus] Registered {} event handler types", handlers.size());
    }

    @Override
    public void publish(DomainEvent event) throws BaseException {
        logger.info("[SimpleEventBus] publish called for event: {}", event.getClass().getName());
        List<EventHandler> eventHandlers = handlers.get(event.getClass());
        if (eventHandlers != null) {
            for (EventHandler handler : eventHandlers) {
                handler.handle(event);
            }
        }
    }

    @Override
    public void subscribe(Class<? extends DomainEvent> eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    @Override
    public void unsubscribe(Class<? extends DomainEvent> eventType, EventHandler handler) {
        List<EventHandler> eventHandlers = handlers.get(eventType);
        if (eventHandlers != null) {
            eventHandlers.remove(handler);
        }
    }
}
