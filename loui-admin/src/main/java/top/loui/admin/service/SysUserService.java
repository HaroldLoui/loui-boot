package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysUser;
import top.loui.admin.domain.bo.SysUserBo;
import top.loui.admin.domain.query.SysUserQuery;
import top.loui.admin.domain.vo.LoginUserVo;
import top.loui.admin.domain.vo.SysUserVo;

/**
 * 系统用户Service层
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取当前登录用户信息
     */
    LoginUserVo me();

    /**
     * 用户分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    PageData<SysUserVo> page(SysUserQuery query);

    /**
     * 新增用户
     *
     * @param bo 用户信息
     */
    boolean add(SysUserBo bo);

    /**
     * 修改用户
     *
     * @param bo 用户信息
     */
    boolean edit(SysUserBo bo);

    /**
     * 用户表单数据
     *
     * @param userId 用户ID
     * @return 用户表单信息
     */
    SysUserBo form(Long userId);

    /**
     * 导出用户
     *
     * @param query 查询条件
     */
    void export(SysUserQuery query, HttpServletResponse response);

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 是否成功
     */
    boolean password(Long userId, String password);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态(1:启用;0:禁用)
     */
    boolean status(Long userId, Integer status);
}
