package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysDict;
import top.loui.admin.domain.bo.SysDictBo;
import top.loui.admin.domain.query.SysDictQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDictVo;

import java.util.List;

/**
 * 字典数据表 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 字典分页列表
     *
     * @param query 查询条件
     */
    PageData<SysDictVo> selectDictPage(SysDictQuery query);

    /**
     * 字典下拉列表
     *
     * @param typeCode 字典类型编码
     */
    List<DropdownListVo> options(String typeCode);

    /**
     * 字典数据表单数据
     *
     * @param id 字典ID
     */
    SysDictVo form(Long id);

    /**
     * 新增字典
     */
    boolean add(SysDictBo bo);

    /**
     * 修改字典
     */
    boolean edit(SysDictBo bo);
}
