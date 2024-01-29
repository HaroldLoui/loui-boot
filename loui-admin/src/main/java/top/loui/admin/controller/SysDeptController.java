package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import top.loui.admin.domain.bo.SysDeptBo;
import top.loui.admin.domain.query.SysDeptQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDeptVo;
import top.loui.admin.service.SysDeptService;

import java.util.Arrays;
import java.util.List;

/**
 * 部门接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/dept")
@RestController
public class SysDeptController extends BaseController {

    private final SysDeptService deptService;

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     */
    @GetMapping
    public String list(SysDeptQuery query) {
        List<SysDeptVo> list = deptService.selectSysDeptList(query);
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public String add(@RequestBody @Validated SysDeptBo bo) {
        boolean results = deptService.add(bo);
        return results ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 获取部门下拉选项
     */
    @GetMapping("/options")
    public String options() {
        List<DropdownListVo> list = deptService.options();
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 修改部门
     *
     * @param deptId 部门ID
     */
    @PutMapping("/{deptId}")
    public String edit(@RequestBody @Validated SysDeptBo bo, @PathVariable Long deptId) {
        bo.setId(deptId);
        boolean results = deptService.edit(bo);
        return results ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 获取部门表单数据
     *
     * @param deptId 部门ID
     */
    @GetMapping("/{deptId}/form")
    public String form(@PathVariable Long deptId) {
        SysDeptBo data = deptService.form(deptId);
        return ok(QUERY_SUCCESS, data);
    }

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号(,)分割
     */
    @DeleteMapping("/{ids}")
    public String delete(@PathVariable Long[] ids) {
        boolean results = deptService.removeByIds(Arrays.asList(ids));
        return results ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }
}
