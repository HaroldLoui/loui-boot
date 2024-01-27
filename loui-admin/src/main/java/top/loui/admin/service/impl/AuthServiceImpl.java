package top.loui.admin.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.loui.admin.domain.SysUser;
import top.loui.admin.domain.bo.LoginForm;
import top.loui.admin.domain.vo.LoginVo;
import top.loui.admin.exceptions.LoginFailedException;
import top.loui.admin.service.AuthService;
import top.loui.admin.service.SysUserService;
import top.loui.admin.utils.AssertUtils;
import top.loui.admin.utils.RedisUtils;
import top.loui.admin.utils.SecureUtils;

import java.awt.Font;
import java.time.Duration;
import java.util.Map;

/**
 * 认证中心业务处理层
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 验证码默认字体
     */
    private static final Font CAPTCHA_DEFAULT_FONT = new Font("Verdana", Font.PLAIN, 32);

    @Value("${sa-token.token-prefix}")
    private String tokenType;

    private final SysUserService userService;

    /**
     * 登陆方法
     *
     * @param loginForm 登录表单实体
     * @return 登录VO
     */
    @Override
    public LoginVo login(LoginForm loginForm) {
        // 校验验证码
        String captchaKey = getCaptchaKey(loginForm.getCaptchaKey());
        AssertUtils.isFalse(RedisUtils.hasKey(captchaKey), () -> new LoginFailedException("验证码已过期"));
        String loginCode = loginForm.getCaptchaCode();
        String captchaCode = RedisUtils.getCacheObject(captchaKey);
        AssertUtils.isFalse(StrUtil.equalsIgnoreCase(loginCode, captchaCode), () -> new LoginFailedException("验证码错误"));
        // 查询用户
        SysUser user = userService.queryChain()
            .eq(SysUser::getUsername, loginForm.getUsername())
            .one();
        // 判断用户是否存在
        AssertUtils.isNull(user, () -> new LoginFailedException("用户不存在"));
        // 校验密码是否正确
        String loginPwd = loginForm.getPassword();
        String password = SecureUtils.decrypt(user.getPassword());
        AssertUtils.isFalse(StrUtil.equals(loginPwd, password), () -> new LoginFailedException("密码错误"));
        // 登录
        StpUtil.login(user.getId());
        // 获取token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 返回数据
        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(tokenInfo.getTokenValue());
        loginVo.setExpires(tokenInfo.getTokenTimeout());
        loginVo.setRefreshToken(tokenInfo.getTokenValue());
        loginVo.setTokenType(tokenType);
        // 删除缓存中的验证码
        if (RedisUtils.delete(captchaKey)) {
            log.debug("删除缓存验证码成功");
        }
        return loginVo;
    }

    /**
     * 获取验证码及校验key
     *
     * @return { "captchaKey": key, "captchaBase64": base64 }
     */
    @Override
    public Map<String, String> getCaptcha() {
        // 生成验证码
        Captcha captcha = new SpecCaptcha(150, 40, 5);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        // Captcha captcha = new ArithmeticCaptcha(150, 40);
        captcha.setFont(CAPTCHA_DEFAULT_FONT);
        // key用于登录校验
        String key = IdUtil.getSnowflakeNextIdStr();
        // 验证码存入redis
        RedisUtils.setCacheObject(getCaptchaKey(key), captcha.text().toUpperCase(), Duration.ofSeconds(60));
        return Map.of("captchaKey", key, "captchaBase64", captcha.toBase64());
    }

    private static String getCaptchaKey(String key) {
        return StrUtil.format("captcha:{}", key);
    }
}
