package top.loui.admin.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户和角色关联表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "sys_user_role")
public class SysUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -4569298621619299312L;

    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

}
