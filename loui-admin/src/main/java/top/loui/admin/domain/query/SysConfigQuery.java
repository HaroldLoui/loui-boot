package top.loui.admin.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.loui.admin.common.page.PageQuery;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigQuery extends PageQuery {

    @Serial
    private static final long serialVersionUID = 5406502427278625866L;

    /**
     * 关键字（参数名称）
     */
    private String keywords;
}
