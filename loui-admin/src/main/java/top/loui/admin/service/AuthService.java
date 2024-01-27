package top.loui.admin.service;

import top.loui.admin.domain.bo.LoginForm;
import top.loui.admin.domain.vo.LoginVo;

import java.util.Map;

/**
 * 认证中心Service层
 */
public interface AuthService {

    /**
     * 登陆方法
     *
     * @param loginForm 登录表单实体
     * @return 登录VO
     */
    LoginVo login(LoginForm loginForm);

    /**
     * 获取验证码及校验key
     *
     * @return { "captchaKey": key, "captchaBase64": base64 }
     */
    Map<String, String> getCaptcha();
}
