package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysRoleTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = -2898522369736301606L;

    /**
     * 角色表
     */
    public static final SysRoleTableDef SYS_ROLE = new SysRoleTableDef();


    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色编码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 角色名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 显示顺序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 角色状态(1-正常；0-停用)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 逻辑删除标识(0-未删除；1-已删除)
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)
     */
    public final QueryColumn DATA_SCOPE = new QueryColumn(this, "data_scope");

    /**
     * 更新时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CODE, SORT, STATUS, DATA_SCOPE, DELETED, CREATE_TIME, UPDATE_TIME};

    public SysRoleTableDef() {
        super("", "sys_role");
    }

}
