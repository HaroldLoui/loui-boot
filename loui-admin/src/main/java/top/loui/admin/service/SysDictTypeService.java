package top.loui.admin.service;

import com.mybatisflex.core.service.IService;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysDictType;
import top.loui.admin.domain.bo.SysDictTypeBo;
import top.loui.admin.domain.query.SysDictTypeQuery;
import top.loui.admin.domain.vo.SysDictTypeVo;

/**
 * 字典类型表 服务层。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 字典类型分页列表
     *
     * @param query 查询条件
     */
    PageData<SysDictTypeVo> selectDictTypesPage(SysDictTypeQuery query);

    /**
     * 字典类型表单数据
     *
     * @param id 字典ID
     */
    SysDictTypeVo form(Long id);

    /**
     * 新增字典类型
     */
    boolean add(SysDictTypeBo bo);

    /**
     * 修改字典类型
     */
    boolean edit(SysDictTypeBo bo);
}
