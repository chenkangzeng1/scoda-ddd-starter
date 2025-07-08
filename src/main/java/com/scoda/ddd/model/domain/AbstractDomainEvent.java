package com.scoda.ddd.model.domain;

import com.scoda.ddd.utils.ScodaSnowflake;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Abstract base class for domain events, encapsulates event ID, type, occurrence time, and common request context for event tracking and extension.
 *
 * @author kangzeng.ckz
 * @since 2024/11/1
 **/
@Data
public abstract class AbstractDomainEvent implements DomainEvent {
    private String eventId;
    private String eventType;
    private LocalDateTime occurredOn;
    /**
     * Request ID
     */
    @Hidden
    private String requestId;

    /**
     * the aliUid of call open api.
     */
    @Hidden
    protected Long callerUid;
    /**
     * Username
     */
    @Hidden
    private String userName;
    /**
     * Authorities
     */
    @Hidden
    private String authorities;
    /**
     * jwt token id
     */
    @Hidden
    private String jti;

    /**
     * Default constructor for AbstractDomainEvent.
     */
    public AbstractDomainEvent() {
        this.eventId = genEventUid();
        this.occurredOn = LocalDateTime.now();
    }

    private String genEventUid() {
        return new ScodaSnowflake(1L, 1L).genEventUid();
    }

} 