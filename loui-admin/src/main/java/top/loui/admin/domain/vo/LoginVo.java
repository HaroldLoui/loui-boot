package top.loui.admin.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录VO
 */
@Data
public class LoginVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 4695455825282456654L;

    /**
     * 登录token
     */
    private String accessToken;

    /**
     * token类型
     */
    private String tokenType;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 有效期
     */
    private Long expires;
}
