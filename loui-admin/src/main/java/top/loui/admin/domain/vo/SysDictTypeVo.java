package top.loui.admin.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysDictTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 9179675134745828134L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
