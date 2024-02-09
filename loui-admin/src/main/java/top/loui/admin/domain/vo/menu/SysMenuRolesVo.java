package top.loui.admin.domain.vo.menu;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMapping;
import io.github.linpeilie.annotations.AutoMappings;
import lombok.Data;
import top.loui.admin.common.tree.FatherSonRelationship;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AutoMapper(target = RouteVo.class, reverseConvertGenerate = false)
@Data
public class SysMenuRolesVo implements Serializable, FatherSonRelationship {

    @Serial
    private static final long serialVersionUID = -7535229701116251426L;

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 父节点ID路径
     */
    private String treePath;

    /**
     * 菜单名称
     */
    @AutoMapping(target = "meta.title")
    private String name;

    /**
     * 菜单类型(1:菜单 2:目录 3:外链 4:按钮)
     */
    private Integer type;

    /**
     * 路由路径(浏览器地址栏路径)
     */
    @AutoMappings({
        @AutoMapping(target = "name", expression = "java(org.dromara.hutool.core.text.StrUtil.upperFirst(source.getPath()))"),
        @AutoMapping(target = "path"),
    })
    private String path;

    /**
     * 组件路径(vue页面完整路径，省略.vue后缀)
     */
    private String component;

    /**
     * 权限标识
     */
    private String perm;

    /**
     * 显示状态(1-显示;0-隐藏)
     */
    @AutoMapping(target = "meta.hidden", expression = "java(Boolean.FALSE.equals(sysMenuRolesVo.getVisible()))")
    private Boolean visible;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单图标
     */
    @AutoMapping(target = "meta.icon")
    private String icon;

    /**
     * 跳转路径
     */
    private String redirect;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 【目录】只有一个子路由是否始终显示(1:是 0:否)
     */
    private Boolean alwaysShow;

    /**
     * 【菜单】是否开启页面缓存(1:是 0:否)
     */
    @AutoMapping(target = "meta.keepAlive")
    private Boolean keepAlive;

    /**
     * 角色列表
     */
    @AutoMapping(target = "meta.roles")
    private List<String> roles;
}
