package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.domain.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联表 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> selectMenuIdsByRoleId(Long roleId);

    /**
     * 删除缓存中用户绑定的角色列表
     */
    void removeCacheRoleList();

    /**
     * 删除缓存中用户绑定的权限列表
     */
    void removeCachePermList();
}
