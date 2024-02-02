package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.loui.admin.common.BaseController;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.bo.SysUserBo;
import top.loui.admin.domain.query.SysUserQuery;
import top.loui.admin.domain.vo.LoginUserVo;
import top.loui.admin.domain.vo.SysUserVo;
import top.loui.admin.domain.vo.UserImportVO;
import top.loui.admin.easyexcel.UserImportListener;
import top.loui.admin.service.SysUserService;
import top.loui.admin.utils.ExcelUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * 用户接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class SysUserController extends BaseController {

    private final SysUserService userService;

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/me")
    public String me() {
        LoginUserVo userVo = userService.me();
        return data(userVo);
    }

    /**
     * 用户分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    @GetMapping("/page")
    public String page(SysUserQuery query) {
        PageData<SysUserVo> page = userService.page(query);
        return ok(QUERY_SUCCESS, page);
    }

    /**
     * 新增用户
     *
     * @param bo 用户信息
     * @return 分页列表
     */
    @SaCheckPermission("sys:user:add")
    @PostMapping
    public String add(@RequestBody @Validated SysUserBo bo) {
        return userService.add(bo) ? ok(INSERT_SUCCESS) : fail(INSERT_FAILED);
    }

    /**
     * 用户表单数据
     *
     * @param userId 用户ID
     * @return 用户表单信息
     */
    @GetMapping("/{userId}/form")
    public String form(@PathVariable("userId") Long userId) {
        SysUserBo data = userService.form(userId);
        return ok(QUERY_SUCCESS, data);
    }

    /**
     * 修改用户
     *
     * @param bo     用户信息
     * @param userId 用户ID
     * @return 分页列表
     */
    @SaCheckPermission("sys:user:edit")
    @PutMapping("/{userId}")
    public String edit(@RequestBody @Validated SysUserBo bo, @PathVariable("userId") Long userId) {
        bo.setId(userId);
        return userService.edit(bo) ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 删除用户
     */
    @SaCheckPermission("sys:user:delete")
    @DeleteMapping("/{ids}")
    public String delete(@PathVariable("ids") Long[] ids) {
        boolean result = userService.removeByIds(Arrays.asList(ids));
        return result ? ok(DELETE_SUCCESS) : fail(DELETE_FAILED);
    }

    /**
     * 导出用户
     *
     * @param query 查询条件
     */
    @GetMapping("/_export")
    public void export(SysUserQuery query, HttpServletResponse response) {
        userService.export(query, response);
    }

    /**
     * 用户导入模板下载
     */
    @GetMapping("/template")
    public void template(HttpServletResponse response) throws IOException {
        userService.downloadTemplate(response);
    }

    /**
     * 导入用户
     *
     * @param deptId 部门ID
     * @param file   导入文件
     */
    @PostMapping("/_import")
    public String export(@RequestParam Long deptId, @RequestPart MultipartFile file) throws IOException {
        UserImportListener listener = new UserImportListener(deptId);
        String msg = ExcelUtils.importExcel(file.getInputStream(), UserImportVO.class, listener);
        return ok(msg);
    }

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     */
    @SaCheckPermission("sys:user:reset_pwd")
    @PatchMapping("/{userId}/password")
    public String password(@PathVariable Long userId, @RequestParam String password) {
        boolean result = userService.password(userId, password);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态(1:启用;0:禁用)
     */
    @PatchMapping("/{userId}/status")
    public String status(@PathVariable Long userId, @RequestParam Integer status) {
        boolean result = userService.status(userId, status);
        return result ? ok(UPDATE_SUCCESS) : fail(UPDATE_FAILED);
    }
}
