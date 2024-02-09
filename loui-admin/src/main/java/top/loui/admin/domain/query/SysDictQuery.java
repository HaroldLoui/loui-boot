package top.loui.admin.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.loui.admin.common.page.PageQuery;

import java.io.Serial;

/**
 * 字典查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictQuery extends PageQuery {

    @Serial
    private static final long serialVersionUID = 2099688646577342856L;

    /**
     * 关键字(字典项名称)
     */
    private String keywords;

    /**
     * 字典类型编码
     */
    private String typeCode;
}
