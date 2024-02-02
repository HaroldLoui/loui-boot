package top.loui.admin.domain.vo.menu;

import lombok.Data;
import top.loui.admin.common.tree.TreeNode;
import top.loui.admin.enums.MenuType;

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
    private MenuType type;
    
    /**
     * 菜单是否可见(1:显示;0:隐藏)
     */
    private Integer visible;
    
    /**
     * 子菜单
     */
    private List<SysMenuTableVo> children;
}