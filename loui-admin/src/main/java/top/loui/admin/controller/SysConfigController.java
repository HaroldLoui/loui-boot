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
import org.springframework.web.bind.annotation.RestController;
import top.loui.admin.common.BaseController;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.bo.SysConfigBo;
import top.loui.admin.domain.query.SysConfigQuery;
import top.loui.admin.domain.vo.SysConfigVo;
import top.loui.admin.service.SysConfigService;

import java.util.Arrays;

/**
 * 配置接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/config")
@RestController
public class SysConfigController extends BaseController {

    private final SysConfigService configService;

    /**
     * 系统配置分页列表
     *
     * @param query 查询参数
     */
    @GetMapping("/page")
    public String page(SysConfigQuery query) {
        PageData<SysConfigVo> page = configService.selectConfigPage(query);
        return ok(QUERY_SUCCESS, page);
    }

    /**
     * 系统配置表单数据
     *
     * @param id 系统配置ID
     */
    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id) {
        SysConfigVo formData = configService.form(id);
        return ok(QUERY_SUCCESS, formData);
    }

    /**
     * 新增系统配置
     */
    @SaCheckPermission("sys:config:add")
    @PostMapping
    public String add(@RequestBody @Validated SysConfigBo bo) {
        boolean result = configService.add(bo);
        return result ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 修改系统配置
     *
     * @param id 系统配置ID
     */
    @SaCheckPermission("sys:config:edit")
    @PutMapping("/{id}")
    public String edit(@RequestBody @Validated SysConfigBo bo, @PathVariable Long id) {
        bo.setId(id);
        boolean result = configService.edit(bo);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 删除系统配置
     *
     * @param ids 系统配置ID，多个以英文(,)分割
     */
    @SaCheckPermission("sys:config:delete")
    @DeleteMapping("/{ids}")
    public String delete(@PathVariable Long[] ids) {
        boolean result = configService.delete(Arrays.asList(ids));
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }
}
