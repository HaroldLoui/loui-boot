package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色和菜单关联表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysRoleMenuTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 8383472025998521601L;

    /**
     * 角色和菜单关联表
     */
    public static final SysRoleMenuTableDef SYS_ROLE_MENU = new SysRoleMenuTableDef();

    /**
     * 菜单ID
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE_ID, MENU_ID};

    public SysRoleMenuTableDef() {
        super("", "sys_role_menu");
    }

}
