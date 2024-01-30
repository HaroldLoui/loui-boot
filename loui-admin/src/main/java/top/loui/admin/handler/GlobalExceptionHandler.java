package top.loui.admin.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.loui.admin.common.Result;
import top.loui.admin.exceptions.BusinessException;
import top.loui.admin.exceptions.LoginFailedException;
import top.loui.admin.utils.JsonUtils;

/**
 * 全局异常捕捉
 *
 * @author hanjinfeng
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error(e.getMessage(), e);
        return JsonUtils.toJsonString(Result.fail(null));
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return JsonUtils.toJsonString(Result.fail(e.getMessage(), null));
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException(LoginFailedException e) {
        log.error(e.getMessage(), e);
        return JsonUtils.toJsonString(Result.fail(e.getMessage(), null));
    }

    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        String message = fieldError == null
                ? Result.FAILED_MESSAGE
                : fieldError.getDefaultMessage();
        log.error(message, e);
        return JsonUtils.toJsonString(Result.fail(message, null));
    }

    @ExceptionHandler(NotRoleException.class)
    public String handleNotRoleException(NotRoleException e) {
        log.error(e.getMessage(), e);
        return JsonUtils.toJsonString(Result.fail(401, "账号所属角色错误", null));
    }

    @ExceptionHandler(NotPermissionException.class)
    public String handleNotPermissionException(NotPermissionException e) {
        log.error(e.getMessage(), e);
        return JsonUtils.toJsonString(Result.fail(401, "账号所属权限错误", null));
    }

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLoginException(NotLoginException e) {
        log.error(e.getLocalizedMessage(), e);
        return switch (e.getType()) {
            case NotLoginException.INVALID_TOKEN -> JsonUtils.toJsonString(Result.fail(401, "当前Token无效，请重新登录", null));
            case NotLoginException.TOKEN_TIMEOUT -> JsonUtils.toJsonString(Result.fail(401, "当前登录已过期，请重新登录", null));
            case NotLoginException.BE_REPLACED -> JsonUtils.toJsonString(Result.fail(401, "您已在其他地方登录", null));
            case NotLoginException.KICK_OUT -> JsonUtils.toJsonString(Result.fail(401, "您已被强制下线", null));
            default -> JsonUtils.toJsonString(Result.fail(401, "当前会话未登录", null));
        };
    }
}