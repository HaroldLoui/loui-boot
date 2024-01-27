package top.loui.admin.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色和菜单关联表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@Data
@Table(value = "sys_role_menu")
public class SysRoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = -215217359501397783L;

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

    /**
     * 菜单ID
     */
    @Id
    private Long menuId;

}
