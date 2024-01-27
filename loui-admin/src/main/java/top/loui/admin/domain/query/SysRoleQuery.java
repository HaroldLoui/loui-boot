package top.loui.admin.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.loui.admin.common.page.PageQuery;

import java.io.Serial;

/**
 * 角色分页列表查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQuery extends PageQuery {

    @Serial
    private static final long serialVersionUID = -5410050120606464684L;

    /**
     * 关键字(角色名称/角色编码)
     */
    private String keywords;
}
