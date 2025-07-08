package com.scoda.ddd.model.base.error;

/**
 * Basic business exception, supports error code and message encapsulation, unified exception system.
 * 
 * @author kangzeng.ckz
 * @since 2021/12/1
 */
public class BaseException extends Exception {
    /** Error code */
    private String errorCode;

    /**
     * Default constructor for BaseException.
     */
    public BaseException() {
        super();
    }

    /**
     * Constructor with message.
     * @param message the error message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param cause the cause of the exception
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message and cause.
     * @param message the error message
     * @param cause the cause of the exception
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with error code and message.
     * @param code the error code
     * @param message the error message
     */
    public BaseException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Constructor with error code, message and cause.
     * @param code the error code
     * @param message the error message
     * @param cause the cause of the exception
     */
    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    /**
     * Returns the error code.
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
} 