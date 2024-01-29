package top.loui.admin.common.tree;

/**
 * 有父子关系
 */
public interface FatherSonRelationship {

    /**
     * 获取当前节点的ID
     */
    Object getId();

    /**
     * 获取当前节点的父ID
     */
    Object getParentId();
}
