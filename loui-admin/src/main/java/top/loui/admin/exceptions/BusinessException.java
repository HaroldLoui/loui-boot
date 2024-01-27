package top.loui.admin.exceptions;

import java.io.Serial;

/**
 * 业务异常类
 *
 * @author hanjinfeng
 */
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6197031868122418345L;

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}