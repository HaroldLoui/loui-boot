package top.loui.admin.domain.vo.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import top.loui.admin.common.tree.TreeNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单路由
 */
@Data
public class RouteVo implements Serializable, TreeNode<RouteVo> {

    @Serial
    private static final long serialVersionUID = -3957349747949978404L;
    
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 跳转链接
     */
    private String redirect;

    /**
     * 排序
     */
    @JsonIgnore
    private Integer sort;

    /**
     * 路由属性类型
     */
    private MataVo meta;

    /**
     * 子路由列表
     */
    private List<RouteVo> children;
}
