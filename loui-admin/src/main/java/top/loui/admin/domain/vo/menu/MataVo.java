package top.loui.admin.domain.vo.menu;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 路由属性
 */
@Data
public class MataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2433184677530174625L;

    /**
     * 路由title
     */
    private String title;

    /**
     * ICON
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 拥有路由权限的角色编码
     */
    private List<String> roles;

    /**
     * 是否开启缓存
     */
    private Boolean keepAlive;
}
