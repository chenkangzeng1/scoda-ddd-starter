package com.scoda.ddd.model.base.error;

/**
 * Basic error code enumeration, defines common error types and descriptions for global exception handling.
 * 
 * @author kangzeng.ckz
 * @since 2021/12/1
 **/
public enum BaseErrorCode implements IErrorCode {

    /**
     * Parameter error
     */
    PARAMS_ILLEGAL("1000", "Parameters illegal"),

    /**
     * HTTP return error
     */
    HTTP_REQUEST_FAILED("1001", "HTTP_REQUEST_FAILED"),

    /**
     * Database operation error
     */
    DATABASE_ERROR("1002", "DATABASE_ERROR"),

    /**
     * Enum does not exist
     */
    ILLEGAL_ENUM_ERROR("1003", "ILLEGAL_ENUM_ERROR"),

    /**
     * Internal error
     */
    INTERNAL_ERROR("500", "INTERNAL_ERROR"),
    /**
     * State machine error
     */
    STATEMACHINE_ERROR("1005", "STATEMACHINE_ERROR"),
    /**
     * Command bus class not found (or other)
     */
    BUS_CLASS_NOT_FOUND_ERROR("1005", "BUS_CLASS_NOT_FOUND_ERROR"),
    /**
     * Authentication failure
     */
    AUTH_FAILURE_ERROR("1006", "AUTH_FAILURE_ERROR"),

    /**
     * Feature not implemented
     */
    APP_FUNCTION_NOT_SUPPORT("1007", "APP_FUNCTION_NOT_SUPPORT"),
    ;

    /** Error code */
    private String code;
    
    /** Error message */
    private String message;

    private BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
} 