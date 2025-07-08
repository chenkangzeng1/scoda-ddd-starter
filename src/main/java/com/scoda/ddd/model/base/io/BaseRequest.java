package com.scoda.ddd.model.base.io;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Base class for common request parameters, encapsulates request ID, user info, etc., for easy extension.
 * 
 * @author kangzeng.ckz
 * @since 2025/7/3
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -5532460938533182975L;
    
    final static int DEFAULT_PAGE_SIZE = 20;
    final static int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Request ID
     */
    @Schema(hidden = true)
    private String requestId;

    /**
     * the aliUid of call open api.
     */
    @Schema(hidden = true)
    protected Long callerUid;
    /**
     * 1. partner: BID account
     * 2. customer: cloud account
     * 3. sub: RAM sub-user
     * 4. AssumedRoleUser: STS Token temporary identity
     */
    @Schema(hidden = true)
    private String userName;
    
    /**
     * Authorities
     */
    @Schema(hidden = true)
    private String authorities;
    
    /**
     * JWT token ID
     */
    @Schema(hidden = true)
    private String jti;

} 