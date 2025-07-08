package com.scoda.ddd.model.cqrs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Base class for queries, encapsulates common request parameters, the base parent class for all Queries, for unified extension and management.
 *
 * @author kangzeng.ckz
 * @since 2024/10/24
 **/
@Data
public abstract class BaseQuery<R> implements Query<R> {
    private static final long serialVersionUID = -5532460938533182975L;

    /**
     * Request ID
     */
    @ApiModelProperty(hidden = true)
    private String requestId;

    /**
     * the aliUid of call open api.
     */
    @ApiModelProperty(hidden = true)
    protected Long callerUid;
    /**
     * Username
     */
    @ApiModelProperty(hidden = true)
    private String userName;
    /**
     * Authorities
     */
    @ApiModelProperty(hidden = true)
    private String authorities;
    /**
     * jwt token id
     */
    @ApiModelProperty(hidden = true)
    private String jti;
} 