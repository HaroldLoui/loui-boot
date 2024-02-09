package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.loui.admin.common.BaseController;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.bo.SysRoleBo;
import top.loui.admin.domain.query.SysRoleQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysRoleVo;
import top.loui.admin.service.SysRoleMenuService;
import top.loui.admin.service.SysRoleService;

import java.util.Arrays;
import java.util.List;

/**
 * 角色接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@RestController
public class SysRoleController extends BaseController {

    private final SysRoleService roleService;
    private final SysRoleMenuService roleMenuService;

    /**
     * 角色分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    @GetMapping("/page")
    public String page(SysRoleQuery query) {
        PageData<SysRoleVo> page = roleService.page(query);
        return ok(QUERY_SUCCESS, page);
    }

    /**
     * 角色下拉列表
     *
     * @return 下拉列表
     */
    @GetMapping("/options")
    public String options() {
        List<DropdownListVo> list = roleService.options();
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("sys:role:add")
    @PostMapping
    public String add(@RequestBody @Validated SysRoleBo bo) {
        boolean result = roleService.add(bo);
        if (result) {
            // 删除缓存中用户绑定的角色列表
            roleMenuService.removeCacheRoleList();
        }
        return result ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 角色表单数据
     */
    @GetMapping("/{roleId}/form")
    public String form(@PathVariable Long roleId) {
        SysRoleBo form = roleService.form(roleId);
        return ok("查询成功", form);
    }

    /**
     * 修改角色
     */
    @SaCheckPermission("sys:role:edit")
    @PutMapping("/{id}")
    public String edit(@RequestBody @Validated SysRoleBo bo, @PathVariable Long id) {
        bo.setId(id);
        boolean result = roleService.edit(bo);
        if (result) {
            // 删除缓存中用户绑定的角色列表
            roleMenuService.removeCacheRoleList();
        }
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 删除角色
     *
     * @param ids 角色ID，多个以英文逗号(,)分割
     */
    @SaCheckPermission("sys:role:delete")
    @DeleteMapping("/{ids}")
    public String delete(@PathVariable Long[] ids) {
        boolean result = roleService.removeByIds(Arrays.asList(ids));
        if (result) {
            // 删除缓存中用户绑定的角色列表
            roleMenuService.removeCacheRoleList();
        }
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     */
    @GetMapping("/{roleId}/menuIds")
    public String menuIds(@PathVariable Long roleId) {
        List<Long> menuIds = roleMenuService.selectMenuIdsByRoleId(roleId);
        return ok(QUERY_SUCCESS, menuIds);
    }

    /**
     * 分配菜单权限给角色
     *
     * @param menuIds 菜单ID列表
     * @param roleId  角色ID
     */
    @SaCheckPermission("sys:role:edit")
    @PutMapping("/{roleId}/menus")
    public String menus(@RequestBody Long[] menuIds, @PathVariable Long roleId) {
        boolean result = roleService.menus(roleId, Arrays.asList(menuIds));
        if (result) {
            // 删除缓存中用户绑定的角色列表
            roleMenuService.removeCacheRoleList();
            // 删除缓存中用户绑定的权限列表
            roleMenuService.removeCachePermList();
        }
        return result ? ok("分配成功") : fail("分配失败");
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1-正常；0-停用)
     */
    @SaCheckPermission("sys:role:edit")
    @PutMapping("/{roleId}/status")
    public String status(@PathVariable Long roleId, @RequestParam Integer status) {
        boolean result = roleService.status(roleId, status);
        if (result) {
            // 删除缓存中用户绑定的角色列表
            roleMenuService.removeCacheRoleList();
        }
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }
}
