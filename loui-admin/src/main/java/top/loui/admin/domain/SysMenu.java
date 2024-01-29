package top.loui.admin.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.github.linpeilie.annotations.AutoMapping;
import io.github.linpeilie.annotations.AutoMappings;
import lombok.Data;
import org.dromara.hutool.extra.spring.SpringUtil;
import top.loui.admin.common.tree.FatherSonRelationship;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;
import top.loui.admin.domain.vo.menu.RouteVo;
import top.loui.admin.domain.vo.menu.SysMenuTableVo;
import top.loui.admin.service.SysRoleService;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单管理 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@AutoMappers({
    @AutoMapper(target = SysMenuTableVo.class, reverseConvertGenerate = false),
    @AutoMapper(target = RouteVo.class, reverseConvertGenerate = false),
})
@Data
@Table(value = "sys_menu")
public class SysMenu implements Serializable, FatherSonRelationship {

    @Serial
    private static final long serialVersionUID = -8772265654283963375L;

    /**
     * 菜单ID
     */
    @AutoMappings({
        @AutoMapping(targetClass = SysMenuTableVo.class, target = "id"),
        @AutoMapping(targetClass = RouteVo.class, target = "meta.roles", expression = "java(SysMenu.getMenuRoles(sysMenu.getId()))"),
    })
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
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
    @AutoMappings({
        @AutoMapping(targetClass = SysMenuTableVo.class, target = "name"),
        @AutoMapping(targetClass = RouteVo.class, target = "meta.title"),
    })
    private String name;

    /**
     * 菜单类型(1:菜单 2:目录 3:外链 4:按钮)
     */
    @AutoMapping(targetClass = SysMenuTableVo.class, target = "type", expression = "java(SysMenuTableVo.getType(source.getType()))")
    private Integer type;

    /**
     * 路由路径(浏览器地址栏路径)
     */
    @AutoMappings({
        @AutoMapping(targetClass = SysMenuTableVo.class, target = "path"),
        @AutoMapping(targetClass = RouteVo.class, target = "name", expression = "java(org.dromara.hutool.core.text.StrUtil.upperFirst(source.getPath()))"),
        @AutoMapping(targetClass = RouteVo.class, target = "path"),
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
    @AutoMappings({
        @AutoMapping(targetClass = SysMenuTableVo.class, target = "visible"),
        @AutoMapping(targetClass = RouteVo.class, target = "meta.hidden", expression = "java(sysMenu.getVisible() == 1)"),
    })
    private Integer visible;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单图标
     */
    @AutoMappings({
        @AutoMapping(targetClass = SysMenuTableVo.class, target = "icon"),
        @AutoMapping(targetClass = RouteVo.class, target = "meta.icon"),
    })
    private String icon;

    /**
     * 跳转路径
     */
    private String redirect;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "NOW()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "NOW()")
    private LocalDateTime updateTime;

    /**
     * 【目录】只有一个子路由是否始终显示(1:是 0:否)
     */
    private Integer alwaysShow;

    /**
     * 【菜单】是否开启页面缓存(1:是 0:否)
     */
    @AutoMapping(targetClass = RouteVo.class, target = "meta.keepAlive", expression = "java(sysMenu.getKeepAlive() == 1)")
    private Integer keepAlive;

    /**
     * 获取菜单绑定的角色列表
     */
    public static List<String> getMenuRoles(Long menuId) {
        SysRoleService roleService = SpringUtil.getBean(SysRoleService.class);
        return roleService.getRolesByMenuId(menuId);
    }
}
