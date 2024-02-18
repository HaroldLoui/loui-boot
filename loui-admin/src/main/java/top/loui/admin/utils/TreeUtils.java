package top.loui.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.util.ObjUtil;
import top.loui.admin.common.tree.BuildTree;
import top.loui.admin.common.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

/**
 * 树形结构工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeUtils {

    private static final Set<Long> SET = new CopyOnWriteArraySet<>();

    /**
     * 构造树形结构
     *
     * @param list 原数据列表
     * @param func 转换方法
     * @param <R>  树节点
     * @param <E>  原对象
     * @return 树形结构数据
     */
    public static <R extends TreeNode<R>, E extends BuildTree> List<R> buildTreeNode(List<E> list, Function<E, R> func) {
        return buildTreeNode(list, func, null);
    }

    /**
     * 构造树形结构
     *
     * @param list 原数据列表
     * @param func 转换方法
     * @param comp 排序器
     * @param <R>  树节点
     * @param <E>  原对象
     * @return 树形结构数据
     */
    public static <R extends TreeNode<R>, E extends BuildTree> List<R> buildTreeNode(List<E> list, Function<E, R> func, Comparator<R> comp) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        list.sort(Comparator.comparing(BuildTree::getParentId));
        List<R> results = new ArrayList<>();
        for (E e : list) {
            if (SET.contains(e.getId())) {
                continue;
            }
            R r = func.apply(e);
            r.setChildren(buildTreeNode(e.getId(), list, func, comp));
            results.add(r);
            SET.add(e.getId());
        }
        SET.clear();
        if (CollUtil.isNotEmpty(results) && ObjUtil.isNotNull(comp)) {
            results.sort(comp);
        }
        return results;
    }

    /**
     * 构造树形结构
     *
     * @param id   当前节点的ID
     * @param list 原数据列表
     * @param func 转换方法
     * @param comp 排序器
     * @param <R>  树节点
     * @param <E>  原对象
     * @return 树形结构数据
     */
    private static <R extends TreeNode<R>, E extends BuildTree> List<R> buildTreeNode(Long id, List<E> list, Function<E, R> func, Comparator<R> comp) {
        if (ObjUtil.isNull(id) || CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<R> results = new ArrayList<>();
        for (E e : list) {
            if (SET.contains(e.getId())) {
                continue;
            }
            if (ObjUtil.equals(id, e.getParentId())) {
                R r = func.apply(e);
                r.setChildren(buildTreeNode(e.getId(), list, func, comp));
                results.add(r);
                SET.add(e.getId());
            }
        }
        if (CollUtil.isNotEmpty(results) && ObjUtil.isNotNull(comp)) {
            results.sort(comp);
        }
        return results;
    }
}
