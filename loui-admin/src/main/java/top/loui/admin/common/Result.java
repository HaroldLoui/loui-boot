package top.loui.admin.common;

import org.springframework.http.HttpStatus;

public record Result<T>(int code, String msg, T data) {

    public static final int SUCCESS_CODE = HttpStatus.OK.value();
    public static final String SUCCESS_MESSAGE = HttpStatus.OK.getReasonPhrase();

    public static final int FAILED_CODE = HttpStatus.BAD_REQUEST.value();
    public static final String FAILED_MESSAGE = HttpStatus.BAD_REQUEST.getReasonPhrase();

    public static <T> Result<T> ok() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(SUCCESS_CODE, msg, null);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(SUCCESS_CODE, msg, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(FAILED_CODE, FAILED_MESSAGE, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(FAILED_CODE, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(FAILED_CODE, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
