package com.scoda.ddd.model.cqrs;

import java.io.Serializable;

/**
 * Query interface, marks the object as a query type, used to encapsulate request parameters for read operations.
 *
 * @author kangzeng.ckz
 * @since 2024/10/24
 **/
public interface Query<R> extends Serializable {
} 