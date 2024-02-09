package top.loui.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import top.loui.admin.common.tree.TreeNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 下拉列表Vo
 */
@Data
public class DropdownListVo implements Serializable, TreeNode<DropdownListVo> {

    @Serial
    private static final long serialVersionUID = -6501615366590961352L;

    /**
     * 选项的值
     */
    private Long value;

    /**
     * 选项的标签
     */
    private String label;

    /**
     * 排序
     */
    @JsonIgnore
    private Integer sort;

    /**
     * 子选项列表
     */
    private List<DropdownListVo> children;
}
