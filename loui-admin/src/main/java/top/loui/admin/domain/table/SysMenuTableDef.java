package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 菜单管理 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysMenuTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = -6520423732477221266L;

    /**
     * 菜单管理
     */
    public static final SysMenuTableDef SYS_MENU = new SysMenuTableDef();


    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜单图标
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 菜单名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 路由路径(浏览器地址栏路径)
     */
    public final QueryColumn PATH = new QueryColumn(this, "path");

    /**
     * 权限标识
     */
    public final QueryColumn PERM = new QueryColumn(this, "perm");

    /**
     * 排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 菜单类型(1:菜单 2:目录 3:外链 4:按钮)
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 显示状态(1-显示;0-隐藏)
     */
    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    /**
     * 父菜单ID
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    /**
     * 跳转路径
     */
    public final QueryColumn REDIRECT = new QueryColumn(this, "redirect");

    /**
     * 父节点ID路径
     */
    public final QueryColumn TREE_PATH = new QueryColumn(this, "tree_path");

    /**
     * 组件路径(vue页面完整路径，省略.vue后缀)
     */
    public final QueryColumn COMPONENT = new QueryColumn(this, "component");

    /**
     * 【菜单】是否开启页面缓存(1:是 0:否)
     */
    public final QueryColumn KEEP_ALIVE = new QueryColumn(this, "keep_alive");

    /**
     * 【目录】只有一个子路由是否始终显示(1:是 0:否)
     */
    public final QueryColumn ALWAYS_SHOW = new QueryColumn(this, "always_show");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PARENT_ID, TREE_PATH, NAME, TYPE, PATH, COMPONENT, PERM, VISIBLE, SORT, ICON, REDIRECT, CREATE_TIME, UPDATE_TIME, ALWAYS_SHOW, KEEP_ALIVE};

    public SysMenuTableDef() {
        super("", "sys_menu");
    }

}
