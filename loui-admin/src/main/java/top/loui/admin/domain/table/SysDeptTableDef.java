package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 部门表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysDeptTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = -1197476995232982171L;

    /**
     * 部门表
     */
    public static final SysDeptTableDef SYS_DEPT = new SysDeptTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 部门名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 显示顺序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 状态(1:正常;0:禁用)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 逻辑删除标识(1:已删除;0:未删除)
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 创建人ID
     */
    public final QueryColumn CREATE_BY = new QueryColumn(this, "create_by");

    /**
     * 父节点id
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    /**
     * 父节点id路径
     */
    public final QueryColumn TREE_PATH = new QueryColumn(this, "tree_path");

    /**
     * 修改人ID
     */
    public final QueryColumn UPDATE_BY = new QueryColumn(this, "update_by");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, PARENT_ID, TREE_PATH, SORT, STATUS, DELETED, CREATE_TIME, UPDATE_TIME, CREATE_BY, UPDATE_BY};

    public SysDeptTableDef() {
        super("", "sys_dept");
    }

}
