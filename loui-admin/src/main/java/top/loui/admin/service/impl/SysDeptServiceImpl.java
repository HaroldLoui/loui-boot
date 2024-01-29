package top.loui.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.domain.SysDept;
import top.loui.admin.domain.bo.SysDeptBo;
import top.loui.admin.domain.query.SysDeptQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDeptVo;
import top.loui.admin.mapper.SysDeptMapper;
import top.loui.admin.service.SysDeptService;
import top.loui.admin.utils.TreeUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static top.loui.admin.domain.table.SysDeptTableDef.SYS_DEPT;

/**
 * 部门表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final Converter converter;

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     */
    @Override
    public List<SysDeptVo> selectSysDeptList(SysDeptQuery query) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_DEPT.DEFAULT_COLUMNS)
            .where(SYS_DEPT.NAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())))
            .and(SYS_DEPT.STATUS.eq(query.getStatus(), ObjUtil.isNotNull(query.getStatus())));
        List<SysDept> list = mapper.selectListByQuery(qw);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return TreeUtils.buildTreeNode(
            list,
            (dept) -> converter.convert(dept, SysDeptVo.class)
        );
    }

    /**
     * 新增部门
     */
    @Override
    public boolean add(SysDeptBo bo) {
        SysDept dept = converter.convert(bo, SysDept.class);
        // 设置父节点ID路径
        Long parentId = bo.getParentId();
        dept.setTreePath(buildTreePath(parentId, parentId.toString()));
        // 设置创建人
        dept.setCreateBy(StpUtil.getLoginIdAsLong());
        return this.save(dept);
    }

    /**
     * 获取部门下拉选项
     */
    @Override
    public List<DropdownListVo> options() {
        return TreeUtils.buildTreeNode(
            mapper.selectAll(),
            (dept) -> {
                DropdownListVo vo = new DropdownListVo();
                vo.setLabel(dept.getName());
                vo.setValue(dept.getId());
                vo.setSort(dept.getSort());
                return vo;
            },
            Comparator.comparing(DropdownListVo::getSort)
        );
    }

    /**
     * 修改部门
     */
    @Override
    public boolean edit(SysDeptBo bo) {
        SysDept dept = converter.convert(bo, SysDept.class);
        // 设置父节点ID路径
        Long parentId = bo.getParentId();
        dept.setTreePath(buildTreePath(parentId, parentId.toString()));
        // 设置创建人
        dept.setUpdateBy(StpUtil.getLoginIdAsLong());
        return this.updateById(dept);
    }

    /**
     * 获取部门表单数据
     *
     * @param deptId 部门ID
     */
    @Override
    public SysDeptBo form(Long deptId) {
        SysDept dept = this.getById(deptId);
        return converter.convert(dept, SysDeptBo.class);
    }

    /**
     * 构造父节点ID路径
     */
    private String buildTreePath(Long parentId, String path) {
        if (ObjUtil.equals(0L, parentId)) {
            return path;
        }
        // 找parentId的上一级parentId
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_DEPT.PARENT_ID)
            .where(SYS_DEPT.ID.eq(parentId));
        Long id = mapper.selectObjectByQueryAs(qw, Long.class);
        if (ObjUtil.isNull(id)) {
            return path;
        }
        return buildTreePath(id, id + "," + path);
    }
}
