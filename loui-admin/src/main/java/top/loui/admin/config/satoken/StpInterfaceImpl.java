package top.loui.admin.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.loui.admin.service.SysMenuService;
import top.loui.admin.service.SysRoleService;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@RequiredArgsConstructor
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    private final SysRoleService roleService;
    private final SysMenuService menuService;

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return menuService.getPerms(loginId);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.getRoles(loginId);
    }

}