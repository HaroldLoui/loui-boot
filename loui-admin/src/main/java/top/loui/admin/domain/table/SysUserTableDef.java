package top.loui.admin.domain.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 用户信息表 表定义层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public class SysUserTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 8166399466971863005L;

    /**
     * 用户信息表
     */
    public static final SysUserTableDef SYS_USER = new SysUserTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 用户邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 用户头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 部门ID
     */
    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");

    /**
     * 性别((1:男;2:女))
     */
    public final QueryColumn GENDER = new QueryColumn(this, "gender");

    /**
     * 联系方式
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 用户状态((1:正常;0:禁用))
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 逻辑删除标识(0:未删除;1:已删除)
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, NICKNAME, GENDER, PASSWORD, DEPT_ID, AVATAR, MOBILE, STATUS, EMAIL, CREATE_TIME, UPDATE_TIME};

    public SysUserTableDef() {
        super("", "sys_user");
    }

}
