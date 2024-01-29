package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
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
import top.loui.admin.domain.bo.SysDictBo;
import top.loui.admin.domain.bo.SysDictTypeBo;
import top.loui.admin.domain.query.SysDictQuery;
import top.loui.admin.domain.query.SysDictTypeQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDictTypeVo;
import top.loui.admin.domain.vo.SysDictVo;
import top.loui.admin.service.SysDictService;
import top.loui.admin.service.SysDictTypeService;

import java.util.Arrays;
import java.util.List;

/**
 * 字典接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/dict")
@RestController
public class SysDictController extends BaseController {

    private final SysDictTypeService dictTypeService;
    private final SysDictService dictService;

    /**
     * 字典类型分页列表
     *
     * @param query 查询条件
     */
    @GetMapping("/types/page")
    public String typesPage(SysDictTypeQuery query) {
        PageData<SysDictTypeVo> page = dictTypeService.selectDictTypesPage(query);
        return ok(QUERY_SUCCESS, page);
    }

    /**
     * 字典类型表单数据
     *
     * @param id 字典ID
     */
    @GetMapping("/types/{id}/form")
    public String typesForm(@PathVariable Long id) {
        SysDictTypeVo formData = dictTypeService.form(id);
        return ok(QUERY_SUCCESS, formData);
    }

    /**
     * 新增字典类型
     */
    @PostMapping("/types")
    public String typesAdd(@RequestBody SysDictTypeBo bo) {
        boolean result = dictTypeService.add(bo);
        return result ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 修改字典类型
     */
    @PutMapping("/types/{id}")
    public String typesEdit(@RequestBody SysDictTypeBo bo, @PathVariable Long id) {
        bo.setId(id);
        boolean result = dictTypeService.edit(bo);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 删除字典类型
     *
     * @param ids 字典类型ID，多个以英文逗号(,)分割
     */
    @DeleteMapping("/types/{ids}")
    public String typesDelete(@PathVariable Long[] ids) {
        boolean result = dictTypeService.removeByIds(Arrays.asList(ids));
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }

    /**
     * 字典分页列表
     *
     * @param query 查询条件
     */
    @GetMapping("/page")
    public String page(SysDictQuery query) {
        PageData<SysDictVo> pageData = dictService.selectDictPage(query);
        return ok(QUERY_SUCCESS, pageData);
    }

    /**
     * 字典下拉列表
     *
     * @param typeCode 字典类型编码
     */
    @GetMapping("/options")
    public String options(@RequestParam String typeCode) {
        List<DropdownListVo> list = dictService.options(typeCode);
        return ok(QUERY_SUCCESS, list);
    }

    /**
     * 字典数据表单数据
     *
     * @param id 字典ID
     */
    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id) {
        SysDictVo formData = dictService.form(id);
        return ok(QUERY_SUCCESS, formData);
    }

    /**
     * 新增字典
     */
    @PostMapping
    public String add(@RequestBody SysDictBo bo) {
        boolean result = dictService.add(bo);
        return result ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 修改字典
     */
    @PutMapping("/{id}")
    public String edit(@RequestBody SysDictBo bo, @PathVariable Long id) {
        bo.setId(id);
        boolean result = dictService.edit(bo);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 删除字典
     */
    @DeleteMapping("/{ids}")
    public String delete(@PathVariable Long[] ids) {
        boolean result = dictService.removeByIds(Arrays.asList(ids));
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }
}
