package com.scoda.ddd.model.base.io;

import com.scoda.ddd.model.base.error.BaseException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Precondition check utility class, provides assertion and parameter validation methods, unified exception throwing.
 *
 * @author kangzeng.ckz
 * @since 2021/12/1
 **/
public class PreChecker {

    /**
     * Checks if a state condition is true.
     * @param expression the condition to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the generic type
     * @throws BaseException if condition is false
     */
    public static <T> void checkState(boolean expression, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (!expression) {
            throw new BaseException(errorCode, errorMessage);
        }
    }
    /**
     * Checks if a condition is true.
     * @param expression the condition to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the generic type
     * @throws BaseException if condition is false
     */
    public static <T> void checkIsTrue(boolean expression, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (!expression) {
            throw new BaseException(errorCode, errorMessage);
        }
    }

    /**
     * Checks if a condition is false.
     * @param expression the condition to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the generic type
     * @throws BaseException if condition is true
     */
    public static <T> void checkIsFalse(boolean expression, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (expression) {
            throw new BaseException(errorCode, errorMessage);
        }
    }

    /**
     * Checks if an object is not null.
     * @param t the object to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the object type
     * @throws BaseException if object is null
     */
    public static <T> void checkNotNull(T t, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (t == null) {
            throw new BaseException(errorCode, errorMessage);
        }
    }

    /**
     * Checks if a string is not blank.
     * @param text the string to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the generic type
     * @throws BaseException if string is blank
     */
    public static <T> void checkStringNotBlank(String text, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (StringUtils.isEmpty(text)) {
            throw new BaseException(errorCode, errorMessage);
        }
    }

    /**
     * Checks if a collection is not empty.
     * @param collection the collection to check
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param <T> the generic type
     * @throws BaseException if collection is empty
     */
    public static <T> void checkNotEmpty(Collection collection, String errorCode, @Nullable String errorMessage) throws BaseException {
        if (collection == null || collection.size() == 0) {
            throw new BaseException(errorCode, errorMessage);
        }
    }
}
