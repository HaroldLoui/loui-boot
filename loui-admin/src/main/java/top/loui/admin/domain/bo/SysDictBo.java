package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import top.loui.admin.domain.SysDict;

import java.io.Serial;
import java.io.Serializable;

@AutoMapper(target = SysDict.class, reverseConvertGenerate = false)
@Data
public class SysDictBo implements Serializable {

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
