package top.loui.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户Vo
 */
@Data
public class SysUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7931512871933463599L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String genderLabel;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 部门名称
     */
    private String deptName;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 角色名称
     */
    private String roleNames;
}
