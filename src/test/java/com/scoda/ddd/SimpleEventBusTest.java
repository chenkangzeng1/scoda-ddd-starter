package com.scoda.ddd;

import com.scoda.ddd.component.SimpleEventBus;
import com.scoda.ddd.model.cqrs.handle.EventHandler;
import com.scoda.ddd.model.domain.DomainEvent;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class SimpleEventBusTest {
    @Test
    void testPublishNullThrowsException() {
        SimpleEventBus bus = new SimpleEventBus(new ArrayList<EventHandler<? extends DomainEvent>>());
        assertThrows(NullPointerException.class, () -> bus.publish(null));
    }
} 