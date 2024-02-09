package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.loui.admin.common.BaseController;
import top.loui.admin.domain.bo.SysMenuBo;
import top.loui.admin.domain.query.SysMenuQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.menu.RouteVo;
import top.loui.admin.domain.vo.menu.SysMenuTableVo;
import top.loui.admin.service.SysMenuService;
import top.loui.admin.service.SysRoleMenuService;

import java.util.List;

/**
 * 菜单接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
@RestController
public class SysMenuController extends BaseController {

    private final SysMenuService menuService;
    private final SysRoleMenuService roleMenuService;

    /**
     * 菜单列表
     *
     * @param query 查询条件
     */
    @GetMapping
    public String list(SysMenuQuery query) {
        List<SysMenuTableVo> list = menuService.selectMenuList(query);
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     */
    @SaCheckPermission("sys:menu:delete")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean result = menuService.deleteMenuById(id);
        if (result) {
            roleMenuService.removeCachePermList();
        }
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }

    /**
     * 新增菜单
     */
    @SaCheckPermission("sys:menu:add")
    @PostMapping
    public String add(@RequestBody SysMenuBo bo) {
        boolean result = menuService.add(bo);
        return result ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 菜单下拉列表
     */
    @GetMapping("/options")
    public String options() {
        List<DropdownListVo> list = menuService.options();
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 路由列表
     */
    @GetMapping("/routes")
    public String routes() {
        List<RouteVo> list = menuService.routes();
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 菜单表单数据
     *
     * @param id 菜单ID
     */
    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id) {
        SysMenuTableVo menuVo = menuService.form(id);
        return ok(QUERY_SUCCESS, menuVo);
    }

    /**
     * 删除菜单
     *
     * @param ids 菜单ID，多个以英文(,)分割
     */
    @SaCheckPermission("sys:menu:delete")
    @DeleteMapping("/{ids}")
    public String deleteByIds(@PathVariable Long[] ids) {
        boolean result = true;
        for (Long id : ids) {
            result = result && menuService.deleteMenuById(id);
        }
        if (result) {
            roleMenuService.removeCachePermList();
        }
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }

    /**
     * 菜单详情
     *
     * @param id 菜单ID
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id) {
        SysMenuTableVo menuVo = menuService.form(id);
        return ok(QUERY_SUCCESS, menuVo);
    }

    /**
     * 修改菜单
     *
     * @param id 菜单ID
     */
    @SaCheckPermission("sys:menu:edit")
    @PutMapping("/{id}")
    public String edit(@RequestBody SysMenuBo bo, @PathVariable Long id) {
        bo.setId(id);
        boolean result = menuService.edit(bo);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 修改菜单显示状态
     *
     * @param menuId  菜单ID
     * @param visible 显示状态(1:显示;0:隐藏)
     */
    @SaCheckPermission("sys:menu:edit")
    @PatchMapping("/{menuId}")
    public String visible(@PathVariable Long menuId, @RequestParam Integer visible) {
        boolean result = menuService.visible(menuId, visible);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }
}
