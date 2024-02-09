package top.loui.admin.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.mybatisflex.core.query.QueryWrapper;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Validator;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import top.loui.admin.domain.SysUser;
import top.loui.admin.domain.SysUserRole;
import top.loui.admin.domain.vo.UserImportVO;
import top.loui.admin.service.SysRoleService;
import top.loui.admin.service.SysUserRoleService;
import top.loui.admin.service.SysUserService;
import top.loui.admin.utils.JsonUtils;
import top.loui.admin.utils.SecureUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static top.loui.admin.domain.table.SysRoleTableDef.SYS_ROLE;
import static top.loui.admin.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static top.loui.admin.domain.table.SysUserTableDef.SYS_USER;

/**
 * 用户导入监听器
 * <p>
 * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E6%9C%80%E7%AE%80%E5%8D%95%E7%9A%84%E8%AF%BB%E7%9A%84%E7%9B%91%E5%90%AC%E5%99%A8">最简单的读的监听器</a>
 *
 * @author haoxr
 * @since 2022/4/10 20:49
 */
@Slf4j
public class UserImportListener extends MyAnalysisEventListener<UserImportVO> {

    // 有效条数
    private final AtomicInteger validCount;

    // 无效条数
    private final AtomicInteger invalidCount;

    // 导入返回信息
    StringBuilder msg = new StringBuilder();

    // 部门ID
    private final Long deptId;
    private final SysUserService userService;
    private final Converter converter;
    private final SysRoleService roleService;
    private final SysUserRoleService userRoleService;

    public UserImportListener(Long deptId) {
        validCount = new AtomicInteger();
        invalidCount = new AtomicInteger();
        this.deptId = deptId;
        this.userService = SpringUtil.getBean(SysUserService.class);
        this.roleService = SpringUtil.getBean(SysRoleService.class);
        this.userRoleService = SpringUtil.getBean(SysUserRoleService.class);
        this.converter = SpringUtil.getBean(Converter.class);
    }

    /**
     * 每一条数据解析都会来调用
     * <p>
     * 1. 数据校验；全字段校验
     * 2. 数据持久化；
     *
     * @param userImportVO 一行数据，类似于 {@link AnalysisContext#readRowHolder()}
     */
    @Override
    public void invoke(UserImportVO userImportVO, AnalysisContext analysisContext) {
        log.info("解析到一条用户数据:{}", JsonUtils.toJsonString(userImportVO));
        // 校验数据
        StringBuilder validationMsg = new StringBuilder();

        String username = userImportVO.getUsername();
        if (StrUtil.isBlank(username)) {
            validationMsg.append("用户名为空；");
        } else {
            long count = userService.count(SYS_USER.USERNAME.eq(username));
            if (count > 0) {
                validationMsg.append("用户名已存在；");
            }
        }

        String nickname = userImportVO.getNickname();
        if (StrUtil.isBlank(nickname)) {
            validationMsg.append("用户昵称为空；");
        }

        String mobile = userImportVO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            validationMsg.append("手机号码为空；");
        } else {
            if (!Validator.isMobile(mobile)) {
                validationMsg.append("手机号码不正确；");
            }
        }

        if (validationMsg.isEmpty()) {
            // 校验通过，持久化至数据库
            SysUser entity = converter.convert(userImportVO, SysUser.class);
            entity.setDeptId(deptId);   // 部门
            entity.setPassword(SecureUtils.encrypt("123456"));   // 默认密码
            // 性别翻译
            // String genderLabel = userImportVO.getGenderLabel();
            // if (StrUtil.isNotBlank(genderLabel)) {
            //     Integer genderValue = (Integer) IBaseEnum.getValueByLabel(genderLabel, GenderEnum.class);
            //     entity.setGender(genderValue);
            // }

            // 角色解析
            String roleCodes = userImportVO.getRoleCodes();
            List<Long> roleIds = null;
            if (StrUtil.isNotBlank(roleCodes)) {
                String[] codes = roleCodes.split(",");
                QueryWrapper qw = QueryWrapper.create()
                        .select(SYS_ROLE.ID)
                        .from(SYS_ROLE)
                        .where(SYS_ROLE.CODE.in(Arrays.asList(codes)))
                        .and(SYS_ROLE.STATUS.eq(1));
                roleIds = roleService.listAs(qw, Long.class);
            }
            boolean saveResult = userService.save(entity);
            if (saveResult) {
                validCount.getAndIncrement();
                // 保存用户角色关联
                if (CollUtil.isNotEmpty(roleIds)) {
                    List<SysUserRole> userRoles = roleIds.stream()
                            .map(roleId -> new SysUserRole(entity.getId(), roleId))
                            .collect(Collectors.toList());
                    userRoleService.remove(SYS_USER_ROLE.USER_ID.eq(entity.getId()));
                    userRoleService.saveBatch(userRoles);
                }
            } else {
                invalidCount.getAndIncrement();
                msg.append("第").append(validCount.get() + invalidCount.get()).append("行数据保存失败；<br/>");
            }
        } else {
            invalidCount.getAndIncrement();
            msg.append("第").append(validCount.get() + invalidCount.get()).append("行数据校验失败：").append(validationMsg).append("<br/>");
        }
    }


    /**
     * 所有数据解析完成会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) { }


    @Override
    public String getMsg() {
        // 总结信息
        return StrUtil.format("导入用户结束：成功{}条，失败{}条；<br/>{}", validCount, invalidCount, msg);
    }
}