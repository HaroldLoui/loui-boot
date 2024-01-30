package top.loui.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.lang.Validator;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysUser;
import top.loui.admin.domain.SysUserRole;
import top.loui.admin.domain.bo.SysUserBo;
import top.loui.admin.domain.query.SysUserQuery;
import top.loui.admin.domain.vo.LoginUserVo;
import top.loui.admin.domain.vo.SysUserVo;
import top.loui.admin.mapper.SysUserMapper;
import top.loui.admin.service.SysMenuService;
import top.loui.admin.service.SysRoleService;
import top.loui.admin.service.SysUserRoleService;
import top.loui.admin.service.SysUserService;
import top.loui.admin.utils.AssertUtils;
import top.loui.admin.utils.SecureUtils;

import java.util.List;

import static top.loui.admin.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static top.loui.admin.domain.table.SysUserTableDef.SYS_USER;

/**
 * 系统用户业务处理层
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysRoleService roleService;
    private final SysUserRoleService userRoleService;
    private final SysMenuService menuService;
    private final Converter converter;

    /**
     * 获取当前登录用户信息
     */
    @Override
    public LoginUserVo me() {
        // 查询当前登录用户
        SysUser user = this.getById(StpUtil.getLoginIdAsLong());
        // 用户ID
        Long userId = user.getId();
        // 查询登录用户绑定角色列表
        List<String> roles = roleService.getRoles(userId);
        // 查询登录用户绑定权限列表
        List<String> perms = menuService.getPerms(userId);
        // 返回数据
        LoginUserVo userVo = new LoginUserVo();
        userVo.setUserId(userId);
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        userVo.setGender(user.getGender());
        userVo.setRoles(roles);
        userVo.setPerms(perms);
        return userVo;
    }

    /**
     * 用户分页列表
     *
     * @param query 查询条件
     * @return 分页列表
     */
    @Override
    public PageData<SysUserVo> page(SysUserQuery query) {
        // 构造查询条件
        QueryWrapper qw = buildQueryWrapperByQuery(query);
        // 查询数据
        Page<SysUser> page = mapper.paginate(query.buildPage(), qw);
        // 返回指定格式的分页数据
        return PageData.pageAs(page, (user) -> {
            SysUserVo vo = converter.convert(user, SysUserVo.class);
            vo.setGenderLabel("男");
            vo.setDeptName("");
            vo.setRoleNames("");
            return vo;
        });
    }

    /**
     * 新增用户
     *
     * @param bo 用户信息
     */
    // @Transactional
    @Override
    public boolean add(SysUserBo bo) {
        // 校验操作
        checkUsernameUnique(null, bo.getUsername());
        checkMobileUnique(null, bo.getMobile());
        checkEmail(bo.getEmail());
        // Bo转实体
        SysUser user = converter.convert(bo, SysUser.class);
        // 设置默认密码
        user.setPassword(SecureUtils.encrypt("123456"));
        // 开启事务
        return Db.tx(() -> {
            // 保存用户信息
            if (this.save(user)) {
                // 保存该用户关联的角色列表
                List<SysUserRole> userRoleList = getUserRoleList(bo.getRoleIds(), user.getId());
                return userRoleService.saveBatch(userRoleList);
            }
            return false;
        });
    }

    /**
     * 修改用户
     *
     * @param bo 用户信息
     */
    @Override
    public boolean edit(SysUserBo bo) {
        // 校验操作
        AssertUtils.isNull(bo.getId(), "用户ID不能为空");
        checkUsernameUnique(bo.getId(), bo.getUsername());
        checkMobileUnique(bo.getId(), bo.getMobile());
        checkEmail(bo.getEmail());
        // Bo转实体
        SysUser user = converter.convert(bo, SysUser.class);
        // 开启事务
        return Db.tx(() -> {
            // 更新用户信息
            if (this.updateById(user)) {
                // 先删除关联的角色
                if (userRoleService.remove(SYS_USER_ROLE.USER_ID.eq(user.getId()))) {
                    // 再保存关联的角色列表
                    List<SysUserRole> userRoleList = getUserRoleList(bo.getRoleIds(), user.getId());
                    return userRoleService.saveBatch(userRoleList);
                }
            }
            return false;
        });
    }

    /**
     * 用户表单数据
     *
     * @param userId 用户ID
     * @return 用户表单信息
     */
    @Override
    public SysUserBo form(Long userId) {
        // 查询用户信息
        SysUser user = this.getById(userId);
        // 转为表单数据
        SysUserBo formData = converter.convert(user, SysUserBo.class);
        // 查询用户绑定的角色列表
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_USER_ROLE.ROLE_ID)
            .from(SYS_USER_ROLE)
            .where(SYS_USER_ROLE.USER_ID.eq(userId));
        List<Long> roleIds = userRoleService.listAs(qw, Long.class);
        formData.setRoleIds(roleIds);
        // 返回数据
        return formData;
    }

    /**
     * 导出用户
     *
     * @param query 查询条件
     */
    @Override
    public void export(SysUserQuery query, HttpServletResponse response) {
        // 构造查询条件
        QueryWrapper qw = buildQueryWrapperByQuery(query);
        // 查询列表
        List<SysUser> userList = mapper.selectListByQuery(qw);
    }

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 是否成功
     */
    @Override
    public boolean password(Long userId, String password) {
        return UpdateChain.of(SysUser.class)
            .set(SYS_USER.PASSWORD, SecureUtils.encrypt(password))
            .where(SYS_USER.ID.eq(userId))
            .update();
    }

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态(1:启用;0:禁用)
     */
    @Override
    public boolean status(Long userId, Integer status) {
        return UpdateChain.of(SysUser.class)
            .set(SYS_USER.STATUS, status)
            .where(SYS_USER.ID.eq(userId))
            .update();
    }

    /**
     * 构造查询条件
     *
     * @param query 查询参数
     * @return queryWrapper
     */
    private QueryWrapper buildQueryWrapperByQuery(SysUserQuery query) {
        return QueryWrapper.create()
            .select(SYS_USER.DEFAULT_COLUMNS)
            .where(SYS_USER.USERNAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords()))
                .or(SYS_USER.NICKNAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())))
                .or(SYS_USER.MOBILE.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())))
            )
            .and(SYS_USER.STATUS.eq(query.getStatus(), ObjUtil.isNotNull(query.getStatus())))
            .and(SYS_USER.DEPT_ID.eq(query.getDeptId(), ObjUtil.isNotNull(query.getDeptId())));
    }

    /**
     * 检查用户名是否唯一
     *
     * @param username 用户名
     */
    private void checkUsernameUnique(Long userId, String username) {
        SysUser sysUser = mapper.selectOneByCondition(SYS_USER.USERNAME.eq(username));
        AssertUtils.isTrue(
            ObjUtil.isNotNull(sysUser) && ObjUtil.notEquals(sysUser.getId(), userId),
            "用户名重复"
        );
    }

    /**
     * 检查手机号是否唯一
     *
     * @param mobile 手机号
     */
    private void checkMobileUnique(Long userId, String mobile) {
        if (StrUtil.isNotEmpty(mobile)) {
            // 校验手机号格式
            AssertUtils.isFalse(Validator.isMobile(mobile), "手机号码格式错误");
            // 判断是否唯一
            SysUser sysUser = mapper.selectOneByCondition(SYS_USER.MOBILE.eq(mobile));
            AssertUtils.isTrue(
                ObjUtil.isNotNull(sysUser) && ObjUtil.notEquals(sysUser.getId(), userId),
                "手机号重复"
            );
        }
    }

    /**
     * 检查邮箱格式
     *
     * @param email 邮箱
     */
    private void checkEmail(String email) {
        if (StrUtil.isNotEmpty(email)) {
            // 校验邮箱格式
            AssertUtils.isFalse(Validator.isEmail(email), "邮箱格式错误");
        }
    }

    /**
     * List<Long> 转 List<SysUserRole>
     *
     * @param roleIds 角色Id列表
     * @param userId  用户Id
     * @return List<SysUserRole>
     */
    private List<SysUserRole> getUserRoleList(List<Long> roleIds, Long userId) {
        return roleIds
            .stream()
            .map((roleId) -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                return userRole;
            })
            .toList();
    }
}
