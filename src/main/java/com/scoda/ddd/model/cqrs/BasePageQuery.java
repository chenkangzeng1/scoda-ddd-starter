package com.scoda.ddd.model.cqrs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Base class for paginated queries, extends pagination parameters, suitable for paginated Query scenarios, for unified pagination logic.
 *
 * @author kangzeng.ckz
 * @since 2024/10/24
 **/
@Data
public abstract class BasePageQuery<R> extends BaseQuery<R> {
    private static final long serialVersionUID = -5532460938533182975L;
    private final static int MAX_PAGE_SIZE = 200;
    private final static int DEFAULT_PAGE_SIZE = 20;
    private final static int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Page number
     */
    @Schema(description = "Page number, default 1", example = "1")
    private Integer pageNumber = 1;

    /**
     * Page size
     */
    @Schema(description = "Page size, default 20", example = "20")
    private Integer pageSize = 20;

    /**
     * Returns the page size with default value.
     * @return the page size with default value
     */
    public int getPageSizeWithDefault() {
        return (pageSize == null || pageSize <= 0 || pageSize > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * Returns the page number with default value.
     * @return the page number with default value
     */
    public int getPageNumberWithDefault() {
        return (pageNumber == null || pageNumber <= 0) ? DEFAULT_PAGE_NUMBER : pageNumber;
    }
} 