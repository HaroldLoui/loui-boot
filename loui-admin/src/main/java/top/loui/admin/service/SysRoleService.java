package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysRole;
import top.loui.admin.domain.bo.SysRoleBo;
import top.loui.admin.domain.query.SysRoleQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysRoleVo;

import java.util.List;

/**
 * 角色表 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<String> getRoles(Object userId);

    /**
     * 角色分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    PageData<SysRoleVo> page(SysRoleQuery query);

    /**
     * 角色下拉列表
     *
     * @return 下拉列表
     */
    List<DropdownListVo> options();

    /**
     * 新增角色
     */
    boolean add(SysRoleBo bo);

    /**
     * 角色表单数据
     *
     * @param roleId 角色ID
     */
    SysRoleBo form(Long roleId);

    /**
     * 修改角色
     */
    boolean edit(SysRoleBo bo);

    /**
     * 分配菜单权限给角色
     *
     * @param menuIds 菜单ID列表
     * @param roleId  角色ID
     * @return 是否成功
     */
    boolean menus(Long roleId, List<Long> menuIds);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1-正常；0-停用)
     * @return 是否成功
     */
    boolean status(Long roleId, Integer status);

    /**
     * 获取菜单绑定的角色列表
     *
     * @param menuId 菜单ID
     * @return 角色列表
     */
    List<String> getRolesByMenuId(Long menuId);
}
