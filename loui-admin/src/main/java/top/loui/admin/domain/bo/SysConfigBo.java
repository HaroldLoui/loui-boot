package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import top.loui.admin.domain.SysConfig;

import java.io.Serial;
import java.io.Serializable;

/**
 * 参数配置表 实体类。
 *
 * @author hanjinfeng39
 * @since 1.0
 */
@AutoMapper(target = SysConfig.class, reverseConvertGenerate = false)
@Data
public class SysConfigBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8195190439212787652L;

    /**
     * 参数主键
     */
    private Long id;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名不能为空")
    private String configKey;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    /**
     * 备注
     */
    private String remark;
}
