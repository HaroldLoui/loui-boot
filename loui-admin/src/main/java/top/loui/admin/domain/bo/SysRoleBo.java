package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import top.loui.admin.domain.SysRole;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新增系统角色
 */
@AutoMapper(target = SysRole.class)
@Data
public class SysRoleBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7531629801652405948L;

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 角色状态(1-正常；0-停用)
     */
    private Integer status;

    /**
     * 数据权限
     */
    private Integer dataScope;
}