package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.domain.SysMenu;
import top.loui.admin.domain.bo.SysMenuBo;
import top.loui.admin.domain.query.SysMenuQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.menu.RouteVo;
import top.loui.admin.domain.vo.menu.SysMenuTableVo;

import java.util.List;

/**
 * 菜单管理 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户的权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> getPerms(Object userId);

    /**
     * 查询菜单列表
     *
     * @param query 查询条件
     * @return 菜单列表
     */
    List<SysMenuTableVo> selectMenuList(SysMenuQuery query);

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 是否成功
     */
    boolean deleteMenuById(Long id);

    /**
     * 新增菜单
     */
    boolean add(SysMenuBo bo);

    /**
     * 菜单下拉列表
     */
    List<DropdownListVo> options();

    /**
     * 路由列表
     */
    List<RouteVo> routes();

    /**
     * 菜单表单数据
     *
     * @param id 菜单ID
     */
    SysMenuTableVo form(Long id);
}
