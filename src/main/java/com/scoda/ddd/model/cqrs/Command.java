package com.scoda.ddd.model.cqrs;

import java.io.Serializable;

/**
 * Command interface, marks the object as a command type, used to encapsulate request parameters for write operations.
 *
 * @author kangzeng.ckz
 * @since 2024/10/24
 **/
public interface Command<R> extends Serializable {
} 