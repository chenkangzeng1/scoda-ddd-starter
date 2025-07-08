package com.scoda.ddd.model.domain;


import java.util.Collection;

/**
 * DomainEvents description
 *
 * @author kangzeng.ckz
 * @since 2024/10/29
 **/
public interface DomainEvents {
    /**
     * Get all pending domain events
     * @return a collection of pending domain events
     */
    Collection<AbstractDomainEvent> getDomainEvents();

    /**
     * Clear all published domain events
     */
    void clearDomainEvents();
} 