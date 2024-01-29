package top.loui.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.util.ObjUtil;
import top.loui.admin.common.tree.FatherSonRelationship;
import top.loui.admin.common.tree.TreeNode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 树形结构工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeUtils {

    /**
     * 构造树形结构
     *
     * @param id         当前节点的ID
     * @param list       原数据列表
     * @param func       转换方法
     * @param comparator 排序器
     * @param <R>        树节点
     * @param <E>        原对象
     * @return 树形结构数据
     */
    public static <R extends TreeNode<R>, E extends FatherSonRelationship> List<R> buildTreeNode(Object id, List<E> list, Function<E, R> func, Comparator<R> comparator) {
        if (ObjUtil.isNull(id) || CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<E> children = list.stream()
            .filter((menu) -> ObjUtil.notEquals(id, menu.getParentId()))
            .toList();
        return list.stream()
            .filter((menu) -> ObjUtil.equals(id, menu.getParentId()))
            .map((menu) -> {
                R r = func.apply(menu);
                r.setChildren(buildTreeNode(menu.getId(), children, func, comparator));
                return r;
            })
            .sorted(comparator)
            .toList();
    }

    /**
     * 构造树形结构
     *
     * @param id   当前节点的ID
     * @param list 原数据列表
     * @param func 转换方法
     * @param <R>  树节点
     * @param <E>  原对象
     * @return 树形结构数据
     */
    public static <R extends TreeNode<R>, E extends FatherSonRelationship> List<R> buildTreeNode(Object id, List<E> list, Function<E, R> func) {
        if (ObjUtil.isNull(id) || CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<E> children = list.stream()
            .filter((menu) -> ObjUtil.notEquals(id, menu.getParentId()))
            .toList();
        return list.stream()
            .filter((menu) -> ObjUtil.equals(id, menu.getParentId()))
            .map((menu) -> {
                R r = func.apply(menu);
                r.setChildren(buildTreeNode(menu.getId(), children, func));
                return r;
            })
            .toList();
    }
}
