package top.loui.admin.common.tree;

import java.util.List;

/**
 * 树形结构
 */
public interface TreeNode<Node> {

    /**
     * 设置子级节点
     */
    void setChildren(List<Node> children);
}
