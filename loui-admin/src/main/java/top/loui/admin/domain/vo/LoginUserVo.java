package top.loui.admin.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 */
@Data
public class LoginUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7931512871933463599L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别((1:男;2:女))
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户角色编码集合
     */
    private List<String> roles;

    /**
     * 用户权限标识集合
     */
    private List<String> perms;
}
