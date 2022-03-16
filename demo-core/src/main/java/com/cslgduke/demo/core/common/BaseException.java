package com.cslgduke.demo.core.common;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author i565244
 */
public class BaseException extends  RuntimeException{
    private static final long serialVersionUID = 570191527602767524L;
    private String errorCode = "";
    private Object[] args = new Object[] { };

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(BaseException e) {
        this(e.errorCode, e.getCause());
    }

    public BaseException(String errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public BaseException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, Throwable cause, Object... args) {
        super(cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    protected Locale getCurrentLocale() {
        return null;
    }
}
