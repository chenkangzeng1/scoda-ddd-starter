package com.scoda.ddd.model.cqrs.handle;


import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.domain.DomainEvent;

/**
 * Event bus interface, responsible for publishing and subscribing to domain events, implementing event-driven mechanism, supporting asynchronous processing and decoupling of domain events.
 *
 * @author kangzeng.ckz
 * @since 2024/10/29
 **/
public interface EventBus {
    /**
     * Publishes a domain event.
     * @param event the domain event to publish
     * @throws BaseException if event publishing fails
     */
    void publish(DomainEvent event) throws BaseException;

    /**
     * Subscribes to a domain event type.
     * @param eventType the event type to subscribe to
     * @param handler the event handler
     * @throws BaseException if subscription fails
     */
    void subscribe(Class<? extends DomainEvent> eventType, EventHandler handler) throws BaseException;
    
    /**
     * Unsubscribes from a domain event type.
     * @param eventType the event type to unsubscribe from
     * @param handler the event handler
     * @throws BaseException if unsubscription fails
     */
    void unsubscribe(Class<? extends DomainEvent> eventType, EventHandler handler) throws BaseException;

    ;
} 