package top.loui.admin.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysDictVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -4984452194132852039L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
