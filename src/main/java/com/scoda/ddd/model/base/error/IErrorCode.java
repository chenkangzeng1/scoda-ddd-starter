package com.scoda.ddd.model.base.error;

/**
 * Error code interface, standardizes the way to obtain error codes and messages for unified exception handling.
 * 
 * @author kangzeng.ckz
 * @since 2021/12/1
 */
public interface IErrorCode {
    /**
     * Returns the error code.
     * @return the error code
     */
    String getCode();

    /**
     * Returns the error message.
     * @return the error message
     */
    String getMessage();
} 