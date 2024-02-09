package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 用户和角色关联表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysUserRoleTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 8803816339696318346L;

    /**
     * 用户和角色关联表
     */
    public static final SysUserRoleTableDef SYS_USER_ROLE = new SysUserRoleTableDef();

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{USER_ID, ROLE_ID};

    public SysUserRoleTableDef() {
        super("", "sys_user_role");
    }

}
