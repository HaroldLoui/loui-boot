package top.loui.admin.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录表单实体
 */
@Data
public class LoginForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -4206546349240665547L;
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * captchaKey
     */
    @NotBlank(message = "captchaKey不能为空")
    private String captchaKey;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}
