package top.loui.admin.service.impl;

import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.domain.SysMenu;
import top.loui.admin.common.TreeNode;
import top.loui.admin.domain.bo.SysMenuBo;
import top.loui.admin.domain.query.SysMenuQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.menu.RouteVo;
import top.loui.admin.domain.vo.menu.SysMenuTableVo;
import top.loui.admin.mapper.SysMenuMapper;
import top.loui.admin.service.SysMenuService;
import top.loui.admin.utils.AssertUtils;
import top.loui.admin.utils.RedisUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static top.loui.admin.domain.table.SysMenuTableDef.SYS_MENU;
import static top.loui.admin.domain.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static top.loui.admin.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * 菜单管理 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final Converter converter;

    /**
     * 获取用户的权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<String> getPerms(Object userId) {
        final String key = StrUtil.format("sa-token:perms:{}", userId);
        if (RedisUtils.hasKey(key)) {
            return RedisUtils.getCacheObject(key);
        }
        QueryWrapper permWrapper = QueryWrapper.create()
            .select(QueryMethods.distinct(SYS_MENU.PERM))
            .from(SYS_USER_ROLE.as("ur"))
            .leftJoin(SYS_ROLE_MENU).as("rm").on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
            .leftJoin(SYS_MENU).as("m").on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID))
            .where(SYS_USER_ROLE.USER_ID.eq(userId))
            .and(SYS_MENU.PERM.isNotNull());
        List<String> perms = mapper.selectListByQueryAs(permWrapper, String.class);
        RedisUtils.setCacheObject(key, perms);
        return perms;
    }

    /**
     * 查询菜单列表
     *
     * @param query 查询条件
     * @return 菜单列表
     */
    @Override
    public List<SysMenuTableVo> selectMenuList(SysMenuQuery query) {
        return buildTreeNode(
            0L,
            mapper.selectAll(),
            (menu) -> converter.convert(menu, SysMenuTableVo.class),
            Comparator.comparing(SysMenuTableVo::getSort)
        );
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 是否成功
     */
    @Override
    public boolean deleteMenuById(Long id) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_MENU.DEFAULT_COLUMNS)
            .where(SYS_MENU.PARENT_ID.eq(id));
        List<SysMenu> menuList = mapper.selectListByQuery(qw);
        AssertUtils.isNotEmpty(menuList, "该菜单有子菜单，无法删除");
        return this.removeById(id);
    }

    /**
     * 新增菜单
     */
    @Override
    public boolean add(SysMenuBo bo) {
        SysMenu entity = converter.convert(bo, SysMenu.class);
        // 设置父节点ID路径
        Long parentId = entity.getParentId();
        entity.setTreePath(buildTreePath(parentId, parentId.toString()));
        // 保存到数据库
        return this.save(entity);
    }

    /**
     * 菜单下拉列表
     */
    @Override
    public List<DropdownListVo> options() {
        QueryWrapper qw = QueryWrapper.create().select(SYS_MENU.DEFAULT_COLUMNS);
        List<SysMenu> menuList = mapper.selectListByQuery(qw);
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        return buildTreeNode(
            0L,
            menuList,
            (menu) -> {
                DropdownListVo vo = new DropdownListVo();
                vo.setValue(menu.getId());
                vo.setLabel(menu.getName());
                vo.setSort(menu.getSort());
                return vo;
            },
            Comparator.comparing(DropdownListVo::getSort)
        );
    }

    /**
     * 路由列表
     */
    @Override
    public List<RouteVo> routes() {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_MENU.DEFAULT_COLUMNS)
            .where(SYS_MENU.TYPE.in(1, 2, 3));
        List<SysMenu> menuList = mapper.selectListByQuery(qw);
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        return buildTreeNode(
            0L,
            menuList,
            (menu) -> converter.convert(menu, RouteVo.class),
            Comparator.comparing(RouteVo::getSort)
        );
    }

    /**
     * 菜单表单数据
     *
     * @param id 菜单ID
     */
    @Override
    public SysMenuTableVo form(Long id) {
        SysMenu menu = this.getById(id);
        return converter.convert(menu, SysMenuTableVo.class);
    }

    /**
     * 修改菜单
     */
    @Override
    public boolean edit(SysMenuBo bo) {
        SysMenu entity = converter.convert(bo, SysMenu.class);
        // 设置父节点ID路径
        Long parentId = entity.getParentId();
        entity.setTreePath(buildTreePath(parentId, parentId.toString()));
        // 保存到数据库
        return this.updateById(entity);
    }

    /**
     * 修改菜单显示状态
     *
     * @param menuId  菜单ID
     * @param visible 显示状态(1:显示;0:隐藏)
     */
    @Override
    public boolean visible(Long menuId, Integer visible) {
        return UpdateChain.of(SysMenu.class)
            .set(SYS_MENU.VISIBLE, visible)
            .where(SYS_MENU.ID.eq(menuId))
            .update();
    }

    /**
     * 构造父节点ID路径
     */
    private String buildTreePath(Long parentId, String path) {
        if (ObjUtil.equals(0L, parentId)) {
            return path;
        }
        // 找parentId的上一级parentId
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_MENU.PARENT_ID)
            .where(SYS_MENU.ID.eq(parentId));
        Long id = mapper.selectObjectByQueryAs(qw, Long.class);
        if (ObjUtil.isNull(id)) {
            return path;
        }
        return buildTreePath(id, id + "," + path);
    }

    /**
     * 构造树形菜单列表
     */
    private <R extends TreeNode<R>> List<R> buildTreeNode(Long id, List<SysMenu> list, Function<SysMenu, R> func, Comparator<R> comparator) {
        if (ObjUtil.isNull(id) || CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<SysMenu> children = list.stream()
            .filter((menu) -> ObjUtil.notEquals(id, menu.getParentId()))
            .toList();
        return list.stream()
            .filter((menu) -> ObjUtil.equals(id, menu.getParentId()))
            .map((menu) -> {
                R r = func.apply(menu);
                r.setChildren(buildTreeNode(menu.getId(), children, func, comparator));
                return r;
            })
            .sorted(comparator)
            .toList();
    }
}
