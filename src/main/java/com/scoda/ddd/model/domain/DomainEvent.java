package com.scoda.ddd.model.domain;

import java.time.LocalDateTime;

/**
 * DomainEvent description
 *
 * @author kangzeng.ckz
 * @since 2024/10/25
 **/
public interface DomainEvent {
    /**
     * Get event ID
     * @return the event ID
     */
    String getEventId();

    /**
     * Get event occurrence time
     * @return the time when the event occurred
     */
    LocalDateTime getOccurredOn();

    /**
     * Get event type
     * @return the event type
     */
    String getEventType();
} 