package top.loui.admin.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysRole;
import top.loui.admin.domain.SysRoleMenu;
import top.loui.admin.domain.bo.SysRoleBo;
import top.loui.admin.domain.query.SysRoleQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysRoleVo;
import top.loui.admin.mapper.SysRoleMapper;
import top.loui.admin.service.SysRoleMenuService;
import top.loui.admin.service.SysRoleService;
import top.loui.admin.utils.AssertUtils;
import top.loui.admin.utils.RedisUtils;

import java.util.List;

import static top.loui.admin.domain.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static top.loui.admin.domain.table.SysRoleTableDef.SYS_ROLE;
import static top.loui.admin.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * 角色表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final Converter converter;
    private final SysRoleMenuService roleMenuService;

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<String> getRoles(Object userId) {
        final String key = StrUtil.format("sa-token:roles:{}", userId);
        if (RedisUtils.hasKey(key)) {
            return RedisUtils.getCacheObject(key);
        }
        QueryWrapper qw = QueryWrapper.create()
            .select(QueryMethods.distinct(SYS_ROLE.CODE))
            .from(SYS_USER_ROLE.as("ur"))
            .leftJoin(SYS_ROLE).as("r").on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
            .where(SYS_USER_ROLE.USER_ID.eq(userId));
        List<String> roles = mapper.selectListByQueryAs(qw, String.class);
        RedisUtils.setCacheObject(key, roles);
        return roles;
    }

    /**
     * 角色分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    @Override
    public PageData<SysRoleVo> page(SysRoleQuery query) {
        // 构造查询条件
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_ROLE.DEFAULT_COLUMNS)
            .where(SYS_ROLE.NAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords()))
                .or(SYS_ROLE.CODE.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())))
            );
        // 查询分页数据
        Page<SysRole> page = mapper.paginate(query.buildPage(), qw);
        // 返回指定格式的分页数据
        return PageData.pageAs(page, (role) -> converter.convert(role, SysRoleVo.class));
    }

    /**
     * 角色下拉列表
     *
     * @return 下拉列表
     */
    @Override
    public List<DropdownListVo> options() {
        // 构造查询条件
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_ROLE.ID.as("value"), SYS_ROLE.NAME.as("label"))
            .where(SYS_ROLE.DATA_SCOPE.ne(0));
        // 查询并返回数据
        return mapper.selectListByQueryAs(qw, DropdownListVo.class);
    }

    /**
     * 新增角色
     */
    @Override
    public boolean add(SysRoleBo bo) {
        // 校验操作
        checkRoleNameUnique(null, bo.getName());
        // 插入到数据库
        SysRole entity = converter.convert(bo, SysRole.class);
        return this.save(entity);
    }

    /**
     * 角色表单数据
     *
     * @param roleId 角色ID
     */
    @Override
    public SysRoleBo form(Long roleId) {
        SysRole role = this.getById(roleId);
        return converter.convert(role, SysRoleBo.class);
    }

    /**
     * 修改角色
     *
     * @param bo
     */
    @Override
    public boolean edit(SysRoleBo bo) {
        // 校验操作
        checkRoleNameUnique(bo.getId(), bo.getName());
        // 更新数据库
        SysRole entity = converter.convert(bo, SysRole.class);
        return this.updateById(entity);
    }

    /**
     * 分配菜单权限给角色
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 是否成功
     */
    @Override
    public boolean menus(Long roleId, List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return true;
        }
        List<SysRoleMenu> list = menuIds.stream()
            .map((menuId) -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                return roleMenu;
            })
            .toList();
        // 删除相关缓存
        List<Long> menuIds1 = roleMenuService.selectMenuIdsByRoleId(roleId);
        if (CollUtil.isNotEmpty(menuIds1)) {
            List<String> keys = menuIds1.stream().map(id -> "sys_menu:roles:" + id).toList();
            RedisUtils.delete(keys);
        }
        return Db.tx(() ->
            roleMenuService.remove(SYS_ROLE_MENU.ROLE_ID.eq(roleId)) &&
            roleMenuService.saveBatch(list)
        );
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1-正常；0-停用)
     * @return 是否成功
     */
    @Override
    public boolean status(Long roleId, Integer status) {
        return UpdateChain.of(SysRole.class)
            .set(SYS_ROLE.STATUS, status)
            .where(SYS_ROLE.ID.eq(roleId))
            .update();
    }

    /**
     * 获取菜单绑定的角色列表
     *
     * @param menuId 菜单ID
     * @return 角色列表
     */
    @Override
    public List<String> getRolesByMenuId(Long menuId) {
        final String key = "sys_menu:roles:" + menuId;
        if (RedisUtils.hasKey(key)) {
            return RedisUtils.getCacheObject(key);
        }
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_ROLE.CODE)
            .from(SYS_ROLE_MENU.as("rm"))
            .leftJoin(SYS_ROLE).as("r").on(SYS_ROLE.ID.eq(SYS_ROLE_MENU.ROLE_ID))
            .where(SYS_ROLE_MENU.MENU_ID.eq(menuId));
        List<String> roles = mapper.selectListByQueryAs(qw, String.class);
        RedisUtils.setCacheObject(key, roles);
        return roles;
    }

    /**
     * 检查角色名称是否重复
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     */
    private void checkRoleNameUnique(Long roleId, String roleName) {
        SysRole sysRole = mapper.selectOneByCondition(SYS_ROLE.NAME.eq(roleName));
        AssertUtils.isTrue(
            ObjUtil.isNotNull(sysRole) && ObjUtil.notEquals(sysRole.getId(), roleId),
            "角色名称不能重复"
        );
    }
}
