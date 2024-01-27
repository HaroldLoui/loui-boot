package top.loui.admin.exceptions;

import java.io.Serial;

/**
 * 登录失败异常
 */
public class LoginFailedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 950586383526915153L;

    public LoginFailedException(String message) {
        super(message);
    }
}
