package com.scoda.ddd.model.base.io;

import com.scoda.ddd.model.base.error.BaseErrorCode;
import com.scoda.ddd.model.base.error.BaseException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Parameter validation utility class, supports various common parameter validity checks for quick business integration.
 *
 * @author kangzeng.ckz
 * @since 2021/12/1
 **/
public class ParamChecker {
    /**
     * Checks if an object is not null.
     * @param obj the object to check
     * @param clazz the class type
     * @param <T> the object type
     * @throws BaseException if object is null
     */
    public static <T> void checkNotNull(T obj, Class<T> clazz) throws BaseException {
        if (obj == null) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), clazz.getName() + " is null.");
        }
    }

    /**
     * Checks if a string is not null or empty.
     * @param obj the string to check
     * @param tag the tag for error message
     * @throws BaseException if string is null or empty
     */
    public static void checkNotNull(String obj, String tag) throws BaseException {
        if (StringUtils.isEmpty(obj)) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is null.");
        }
    }

    /**
     * Checks if a string is null or empty.
     * @param obj the string to check
     * @param tag the tag for error message
     * @throws BaseException if string is not null or empty
     */
    public static void checkNull(String obj, String tag) throws BaseException {
        if (!StringUtils.isEmpty(obj)) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " must be null.");
        }
    }

    /**
     * Checks if at least one object is not null.
     * @param tag the tag for error message
     * @param objs the objects to check
     * @throws BaseException if all objects are null
     */
    public static void checkAnyNotNull(String tag, Object... objs) throws BaseException {
        checkNotNull(objs, "objs");
        checkNotNull(tag, "tag");
        boolean notNull = false;
        for (Object obj : objs) {
            if (obj != null) {
                notNull = true;
                break;
            }
        }

        if (!notNull) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " can't all be null.");
        }
    }

    /**
     * Checks if all objects are not null.
     * @param tag the tag for error message
     * @param objs the objects to check
     * @throws BaseException if any object is null
     */
    public static void checkAllNotNull(String tag, Object... objs) throws BaseException {
        checkNotNull(objs, "objs");
        checkNotNull(tag, "tag");
        boolean nullFlag = false;
        for (Object obj : objs) {
            if (obj == null) {
                nullFlag = true;
                break;
            }
        }

        if (nullFlag) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " can't any be null.");
        }
    }

    /**
     * Checks if an object is not null.
     * @param obj the object to check
     * @param tag the tag for error message
     * @throws BaseException if object is null
     */
    public static void checkNotNull(Object obj, String tag) throws BaseException {
        if (obj == null) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is null.");
        }
    }

    /**
     * Checks if an object is null.
     * @param obj the object to check
     * @param tag the tag for error message
     * @throws BaseException if object is not null
     */
    public static void checkNull(Object obj, String tag) throws BaseException {
        if (obj != null) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " must be null.");
        }
    }

    /**
     * Checks if a string is not empty.
     * @param str the string to check
     * @param tag the tag for error message
     * @throws BaseException if string is empty
     */
    public static void checkNotEmpty(String str, String tag) throws BaseException {
        if (str == null || str.length() == 0) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is empty.");
        }
    }

    /**
     * Checks if a list is not empty.
     * @param list the list to check
     * @param tag the tag for error message
     * @throws BaseException if list is empty
     */
    public static void checkNotEmpty(List list, String tag) throws BaseException {
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is empty.");
        }
    }

    /**
     * Checks if an array is not empty.
     * @param list the array to check
     * @param tag the tag for error message
     * @throws BaseException if array is empty
     */
    public static void checkNotEmpty(Object[] list, String tag) throws BaseException {
        if (list == null || list.length == 0) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is empty.");
        }
    }

    /**
     * Checks if an ID is valid (not null and greater than 0).
     * @param obj the ID to check
     * @param tag the tag for error message
     * @throws BaseException if ID is invalid
     */
    public static void checkIdNotInvalid(Object obj, String tag) throws BaseException {
        if (obj == null) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is null.");
        }
        if (obj instanceof Long) {
            if ((Long)obj <= 0) {
                throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is invalid.");
            }
        } else if (obj instanceof Integer) {
            if ((Integer)obj <= 0) {
                throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is invalid.");
            }
        } else {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is invalid.");
        }

    }

    /**
     * Checks if a string length is within the maximum limit.
     * @param str the string to check
     * @param length the maximum length
     * @param tag the tag for error message
     * @throws BaseException if string exceeds maximum length
     */
    public static void checkMaxLength(String str, int length, String tag) throws BaseException {
        if (StringUtils.isEmpty(str)) {
            return;
        }

        if (str.length() > length) {
            throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(), tag + " is longer than " + length);
        }
    }

    /**
     * Checks if a value is in the list.
     * @param value the value to check
     * @param list the list to check against
     * @throws BaseException if value is not in the list
     */
    public static void checkInList(Object value, List<Object> list) throws BaseException {
        for (Object o : list) {
            if (o.equals(value)) {
                return;
            }
        }

        String listStr = list.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(),
                String.valueOf(value) + "不在" + listStr + "中");
    }

    /**
     * Checks if a value is in the list.
     * @param value the value to check
     * @param list the list to check against
     * @param tag the tag for error message
     * @throws BaseException if value is not in the list
     */
    public static void checkInList(Object value, List<Object> list, String tag) throws BaseException {
        for (Object o : list) {
            if (o.equals(value)) {
                return;
            }
        }

        String listStr = list.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        throw new BaseException(BaseErrorCode.PARAMS_ILLEGAL.getCode(),
                tag + ":" + String.valueOf(value) + "不在" + listStr + "中");
    }
} 