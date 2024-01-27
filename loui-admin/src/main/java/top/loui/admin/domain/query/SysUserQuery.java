package top.loui.admin.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.loui.admin.common.page.PageQuery;

import java.io.Serial;

/**
 * 用户分页列表查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserQuery extends PageQuery {

    @Serial
    private static final long serialVersionUID = 8988202931543764211L;

    /**
     * 关键字(用户名/昵称/手机号)
     */
    private String keywords;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Integer deptId;
}
