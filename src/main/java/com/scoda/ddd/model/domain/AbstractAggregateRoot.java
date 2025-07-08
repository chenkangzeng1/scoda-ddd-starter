package com.scoda.ddd.model.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * AbstractAggregateRoot description
 *
 * @author kangzeng.ckz
 * @since 2024/10/29
 **/
public abstract class AbstractAggregateRoot extends AbstractDomainEvent implements DomainEvents {
    private final List<AbstractDomainEvent> domainEvents = new ArrayList<>();

    /**
     * Register domain event
     * @param event the domain event to register
     */
    protected void registerEvent(AbstractDomainEvent event) {
        if (event != null) {
            domainEvents.add(event);
        }
    }

    @Override
    public Collection<AbstractDomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }
} 