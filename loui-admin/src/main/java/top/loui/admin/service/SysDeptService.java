package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.domain.SysDept;
import top.loui.admin.domain.bo.SysDeptBo;
import top.loui.admin.domain.query.SysDeptQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDeptVo;

import java.util.List;

/**
 * 部门表 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     */
    List<SysDeptVo> selectSysDeptList(SysDeptQuery query);

    /**
     * 新增部门
     */
    boolean add(SysDeptBo bo);

    /**
     * 获取部门下拉选项
     */
    List<DropdownListVo> options();

    /**
     * 修改部门
     */
    boolean edit(SysDeptBo bo);

    /**
     * 获取部门表单数据
     *
     * @param deptId 部门ID
     */
    SysDeptBo form(Long deptId);
}
