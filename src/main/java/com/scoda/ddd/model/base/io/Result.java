package com.scoda.ddd.model.base.io;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * General API response wrapper, includes status code, message, data body, supports static factory methods for success/failure.
 * 
 * @author kangzeng.ckz
 * @since 2021/12/2
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -5176440339468817846L;

    /**
     * Success status code.
     */
    public final static String STATUS_SUCCESS = "200";
    
    /**
     * Success message.
     */
    public final static String MESSAGE_SUCCESS = "success";

    /**
     * Common error status code.
     */
    public final static String STATUS_COMMON_ERROR = "550";
    
    /**
     * Common error message.
     */
    public final static String MESSAGE_COMMON_ERROR = "Service internal Error.";

    /**
     * Request ID
     */
    @JsonInclude(NON_NULL)
    @Schema(description = "Request ID", example = "rid-3gz7ogsa91nnltx74zz78j")
    private String requestId;
    
    /**
     * Response code
     */
    @Schema(description = "Response code", example = "200")
    private String code;
    
    /**
     * Response message
     */
    @Schema(description = "Response message", example = "success")
    private String message;

    /**
     * Is success
     */
    @Schema(description = "Is success", example = "true")
    private boolean success;
    
    /**
     * Response data
     */
    @Schema(description = "Response data", example = "")
    private T data;

    /**
     * Creates a successful result with data, code and message.
     * @param data the data
     * @param code the response code
     * @param message the response message
     * @param <F> the data type
     * @return a successful result
     */
    public static <F> Result<F> success(F data, String code, String message) {
        Result<F> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a successful result with data and code.
     * @param data the data
     * @param code the response code
     * @param <F> the data type
     * @return a successful result
     */
    public static <F> Result<F> success(F data, String code) {
        Result<F> result = new Result<>();
        result.setCode(code);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }


    /**
     * Creates a successful result with data.
     * @param data the data
     * @param <F> the data type
     * @return a successful result
     */
    public static <F> Result<F> success(F data) {
        Result<F> result = new Result<>();
        result.setCode(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a successful result.
     * @param <F> the data type
     * @return a successful result
     */
    public static <F> Result<F> success() {
        Result<F> result = new Result<>();
        result.setCode(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setSuccess(true);
        return result;
    }

    /**
     * Creates a failed result with code and message.
     * @param code the error code
     * @param message the error message
     * @param <F> the data type
     * @return a failed result
     */
    public static <F> Result<F> fail(String code, String message) {
        Result<F> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    /**
     * Creates a failed result.
     * @param <F> the data type
     * @return a failed result
     */
    public static <F> Result<F> fail() {
        Result<F> result = new Result<>();
        result.setCode(STATUS_COMMON_ERROR);
        result.setMessage(MESSAGE_COMMON_ERROR);
        result.setSuccess(false);
        return result;
    }

    /**
     * Creates a failed result with code, message and data.
     * @param code the error code
     * @param message the error message
     * @param data the data
     * @param <F> the data type
     * @return a failed result
     */
    public static <F> Result<F> fail(String code, String message, F data) {
        Result<F> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
} 
 