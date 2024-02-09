package top.loui.admin.domain.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysMenuQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 457293283243741992L;

    /**
     * 关键字(菜单名称)
     */
    private String keywords;

    /**
     * 状态(1->显示；0->隐藏)
     */
    private Integer status;
}
