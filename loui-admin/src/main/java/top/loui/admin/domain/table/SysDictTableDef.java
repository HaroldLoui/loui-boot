package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典数据表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysDictTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 5917931629853527043L;

    /**
     * 字典数据表
     */
    public static final SysDictTableDef SYS_DICT = new SysDictTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 字典项名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 字典项值
     */
    public final QueryColumn VALUE = new QueryColumn(this, "value");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 状态(1:正常;0:禁用)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 字典类型编码
     */
    public final QueryColumn TYPE_CODE = new QueryColumn(this, "type_code");

    /**
     * 是否默认(1:是;0:否)
     */
    public final QueryColumn DEFAULTED = new QueryColumn(this, "defaulted");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE_CODE, NAME, VALUE, SORT, STATUS, DEFAULTED, REMARK, CREATE_TIME, UPDATE_TIME};

    public SysDictTableDef() {
        super("", "sys_dict");
    }

}
