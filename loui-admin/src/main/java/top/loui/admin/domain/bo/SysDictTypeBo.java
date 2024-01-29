package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import top.loui.admin.domain.SysDictType;

import java.io.Serial;
import java.io.Serializable;

@AutoMapper(target = SysDictType.class, reverseConvertGenerate = false)
@Data
public class SysDictTypeBo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5097976000922511478L;

    /**
     * 字典类型ID
     */
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型编码
     */
    private String code;

    /**
     * 类型状态(1:启用;0:禁用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
