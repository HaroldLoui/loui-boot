package top.loui.admin.common.tree;

/**
 * 有父子关系，能够构造树形结构
 */
public interface BuildTree {

    /**
     * 获取当前节点的ID
     */
    Long getId();

    /**
     * 获取当前节点的父ID
     */
    Long getParentId();
}
