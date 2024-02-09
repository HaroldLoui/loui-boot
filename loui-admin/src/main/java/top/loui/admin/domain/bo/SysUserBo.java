package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import top.loui.admin.domain.SysUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 新增系统用户
 */
@AutoMapper(target = SysUser.class)
@Data
public class SysUserBo implements Serializable {

    @Serial
    private static final long serialVersionUID = -478933409737115437L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 性别((0:未知;1:男;2:女))
     */
    private Integer gender;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 用户状态((1:正常;0:禁用))
     */
    private Integer status;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 角色ID集合
     */
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;
}
