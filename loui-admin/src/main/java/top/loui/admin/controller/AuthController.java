package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.loui.admin.common.BaseController;
import top.loui.admin.domain.bo.LoginForm;
import top.loui.admin.domain.vo.LoginVo;
import top.loui.admin.service.AuthService;
import top.loui.admin.utils.RedisUtils;

import java.util.Map;

/**
 * 认证中心
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController extends BaseController {

    private final AuthService authService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginForm loginForm) {
        LoginVo loginVo = authService.login(loginForm);
        return ok("登录成功", loginVo);
    }

    /**
     * 注销
     */
    @SaCheckLogin
    @DeleteMapping("/logout")
    public String logout() {
        Object userId = StpUtil.getLoginId();
        StpUtil.logout(userId);
        // 删除缓存
        String[] keys = {
            StrUtil.format("sa-token:perms:{}", userId),
            StrUtil.format("sa-token:roles:{}", userId)
        };
        if (RedisUtils.delete(keys)) {
            log.debug("清除缓存成功");
        }
        return ok("注销成功");
    }

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public String captcha() {
        Map<String, String> captcha = authService.getCaptcha();
        return data(captcha);
    }
}
