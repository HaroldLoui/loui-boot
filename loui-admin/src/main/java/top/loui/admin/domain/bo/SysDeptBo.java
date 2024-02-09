package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.loui.admin.domain.SysDept;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门表Bo 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-29
 */
@AutoMapper(target = SysDept.class)
@Data
public class SysDeptBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8228414887878583984L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    @NotNull(message = "父部门ID不能为空")
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer status;

}