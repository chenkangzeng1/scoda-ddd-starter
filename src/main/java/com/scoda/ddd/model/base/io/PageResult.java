package com.scoda.ddd.model.base.io;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Pagination API response wrapper, extends Result, adds pagination information, suitable for paginated data responses.
 *
 * @author kangzeng.ckz
 * @since 2021/12/2
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PageResult<T> extends Result<T> {

    private static final long serialVersionUID = -5176440339468817846L;
    /**
     * Page size
     */
    @JsonInclude(NON_NULL)
    @Schema(description = "page size", example = "20")
    private Integer pageSize;
    
    /**
     * Page number
     */
    @JsonInclude(NON_NULL)
    @Schema(description = "page number", example = "1")
    private Integer pageNumber;
    
    /**
     * Total count
     */
    @JsonInclude(NON_NULL)
    @Schema(description = "total count", example = "100")
    private Long totalCount;

    /**
     * Creates a successful page result with all parameters.
     * @param data the data
     * @param code the response code
     * @param message the response message
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param totalCount the total count
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success(F data, String code, String message, Integer pageNumber, Integer pageSize,
                                            Long totalCount) {

        PageResult<F> pageResult = new PageResult<>();
        pageResult.setPageNumber(pageNumber);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalCount(totalCount);
        pageResult.setData(data);
        pageResult.setCode(code);
        pageResult.setMessage(message);
        pageResult.setSuccess(true);
        return pageResult;
    }

    /**
     * Creates a successful page result with pagination parameters.
     * @param data the data
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param totalCount the total count
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success(F data, Integer pageNumber, Integer pageSize,
                                            Long totalCount) {
        PageResult<F> pageResult = new PageResult<>();
        pageResult.setPageNumber(pageNumber);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalCount(totalCount);
        pageResult.setData(data);
        pageResult.setCode(STATUS_SUCCESS);
        pageResult.setMessage(MESSAGE_SUCCESS);
        pageResult.setSuccess(true);
        return pageResult;
    }

    /**
     * Creates a successful page result with code and message.
     * @param data the data
     * @param code the response code
     * @param message the response message
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success(F data, String code, String message) {
        PageResult<F> result = new PageResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a successful page result with code.
     * @param data the data
     * @param code the response code
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success(F data, String code) {
        PageResult<F> result = new PageResult<>();
        result.setCode(code);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a successful page result with data.
     * @param data the data
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success(F data) {
        PageResult<F> result = new PageResult<>();
        result.setCode(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a successful page result.
     * @param <F> the data type
     * @return a successful page result
     */
    public static <F> PageResult<F> success() {
        PageResult<F> result = new PageResult<>();
        result.setCode(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        return result;
    }

    /**
     * Creates a failed page result with code and message.
     * @param code the error code
     * @param message the error message
     * @param <F> the data type
     * @return a failed page result
     */
    public static <F> PageResult<F> fail(String code, String message) {
        PageResult<F> result = new PageResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    /**
     * Creates a failed page result.
     * @param <F> the data type
     * @return a failed page result
     */
    public static <F> PageResult<F> fail() {
        PageResult<F> result = new PageResult<>();
        result.setCode(STATUS_COMMON_ERROR);
        result.setMessage(MESSAGE_COMMON_ERROR);
        result.setSuccess(false);
        return result;
    }

    /**
     * Creates a failed page result with code, message and data.
     * @param code the error code
     * @param message the error message
     * @param data the data
     * @param <F> the data type
     * @return a failed page result
     */
    public static <F> PageResult<F> fail(String code, String message, F data) {
        PageResult<F> result = new PageResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
} 
 