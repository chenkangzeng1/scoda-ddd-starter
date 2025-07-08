package com.scoda.ddd.model.cqrs.handle;


import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.domain.DomainEvent;

/**
 * Event handler interface, defines the specific processing logic for domain events, decoupling events and business.
 *
 * @author kangzeng.ckz
 * @since 2024/10/29
 **/
public interface EventHandler<T extends DomainEvent> {
    /**
     * Handles a domain event.
     * @param event the domain event to handle
     * @throws BaseException if event handling fails
     */
    void handle(T event) throws BaseException;
} 