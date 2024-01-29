package top.loui.admin.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.loui.admin.common.page.PageQuery;

import java.io.Serial;

/**
 * 字典类型查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeQuery extends PageQuery {

    @Serial
    private static final long serialVersionUID = 8773970526029393487L;

    /**
     * 关键字(类型名称/类型编码)
     */
    private String keywords;
}
