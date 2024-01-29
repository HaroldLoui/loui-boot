package top.loui.admin.domain.vo;

import lombok.Data;
import top.loui.admin.common.tree.TreeNode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门表Vo 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-29
 */
@Data
public class SysDeptVo implements Serializable, TreeNode<SysDeptVo> {

    @Serial
    private static final long serialVersionUID = -665976830102680601L;

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
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 子部门
     */
    private List<SysDeptVo> children;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
