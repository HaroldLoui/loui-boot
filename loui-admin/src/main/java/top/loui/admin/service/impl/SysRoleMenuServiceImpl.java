package top.loui.admin.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import top.loui.admin.domain.SysRoleMenu;
import top.loui.admin.mapper.SysRoleMenuMapper;
import top.loui.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import top.loui.admin.utils.RedisUtils;

import java.util.List;

import static top.loui.admin.domain.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 角色和菜单关联表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> selectMenuIdsByRoleId(Long roleId) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_ROLE_MENU.MENU_ID)
            .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId));
        return mapper.selectListByQueryAs(qw, Long.class);
    }

    /**
     * 删除缓存中用户绑定的角色列表
     */
    @Override
    public void removeCacheRoleList() {
        try {
            RedisUtils.delete(RedisUtils.keys("sa-token:roles*"));
        } catch (Exception ignored) {}
    }

    /**
     * 删除缓存中用户绑定的权限列表
     */
    @Override
    public void removeCachePermList() {
        try {
            RedisUtils.delete(RedisUtils.keys("sa-token:perms*"));
        } catch (Exception ignored) {}
    }
}
