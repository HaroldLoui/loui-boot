package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 积分任务 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-04-23
 */
public class TPointTaskTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 积分任务
     */
    public static final TPointTaskTableDef T_POINT_TASK = new TPointTaskTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_BY = new QueryColumn(this, "create_by");

    /**
     * 任务名称
     */
    public final QueryColumn TASK_NAME = new QueryColumn(this, "task_name");

    /**
     * 任务类型
     */
    public final QueryColumn TASK_TYPE = new QueryColumn(this, "task_type");

    /**
     * 修改人
     */
    public final QueryColumn UPDATE_BY = new QueryColumn(this, "update_by");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 积分数
     */
    public final QueryColumn POINT_NUMBER = new QueryColumn(this, "point_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TASK_NAME, TASK_TYPE, POINT_NUMBER, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, REMARK};

    public TPointTaskTableDef() {
        super("", "t_point_task");
    }

}
