package com.scoda.ddd.model.base.io;

import lombok.Data;

import java.util.List;

/**
 * Pagination data VO, encapsulates total count and data list, for easy frontend pagination display.
 *
 * @author kangzeng.ckz
 * @since 2021/12/2
 **/
@Data
public class PageVo<T> {
    /** Total count of records */
    private Long total;
    
    /** List of data records */
    private List<T> list;
}
