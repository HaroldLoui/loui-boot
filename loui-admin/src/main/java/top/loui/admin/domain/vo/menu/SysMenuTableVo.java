package top.loui.admin.domain.vo.menu;

import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Data;
import lombok.Getter;
import top.loui.admin.common.tree.TreeNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * MenuVO，菜单视图对象
 */
@Data
public class SysMenuTableVo implements Serializable, TreeNode<SysMenuTableVo> {

    @Serial
    private static final long serialVersionUID = 5191388349068138643L;

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由相对路径
     */
    private String path;

    /**
     * ICON
     */
    private String icon;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 跳转路径
     */
    private String redirect;
    
    /**
     * 按钮权限标识
     */
    private String perm;
    
    /**
     * 菜单排序(数字越小排名越靠前)
     */
    private Integer sort;
    
    /**
     * 菜单类型
     */
    private Type type;
    
    /**
     * 菜单是否可见(1:显示;0:隐藏)
     */
    private Integer visible;
    
    /**
     * 子菜单
     */
    private List<SysMenuTableVo> children;

    @Getter
    @AutoEnumMapper("value")
    public enum Type {
        BUTTON(4),
        CATALOG(2),
        EXTLINK(3),
        MENU(1),
        NULL(0);

        private final Integer value;

        Type(Integer value) {
            this.value = value;
        }
    }

    public static Type getType(Integer type) {
        return switch (type) {
            case 1 -> Type.MENU;
            case 2 -> Type.CATALOG;
            case 3 -> Type.EXTLINK;
            case 4 -> Type.BUTTON;
            default -> Type.NULL;
        };
    }
}