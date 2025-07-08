package com.scoda.ddd.model.base.io;

import lombok.Data;

/**
 * Base class for pagination request parameters, extends pagination-related fields, suitable for paginated query scenarios.
 * 
 * @author kangzeng.ckz
 * @since 2025/7/3
 */
@Data
public class BasePageRequest extends BaseRequest {
    private final static int MAX_PAGE_SIZE = 200;
    private final static int DEFAULT_PAGE_SIZE = 20;
    private final static int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Page number
     */
    private Integer pageNumber = 1;

    /**
     * Page size
     */
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