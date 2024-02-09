package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典类型表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysDictTypeTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 5652386533138789052L;

    /**
     * 字典类型表
     */
    public static final SysDictTypeTableDef SYS_DICT_TYPE = new SysDictTypeTableDef();

    /**
     * 主键 
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 类型编码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 类型名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 状态(0:正常;1:禁用)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CODE, STATUS, REMARK, CREATE_TIME, UPDATE_TIME};

    public SysDictTypeTableDef() {
        super("", "sys_dict_type");
    }

}
