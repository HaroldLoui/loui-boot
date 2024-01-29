package top.loui.admin.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysDictType;
import top.loui.admin.domain.bo.SysDictTypeBo;
import top.loui.admin.domain.query.SysDictTypeQuery;
import top.loui.admin.domain.vo.SysDictTypeVo;
import top.loui.admin.mapper.SysDictTypeMapper;
import top.loui.admin.service.SysDictTypeService;
import top.loui.admin.utils.AssertUtils;

import static top.loui.admin.domain.table.SysDictTypeTableDef.SYS_DICT_TYPE;

/**
 * 字典类型表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final Converter converter;

    /**
     * 字典类型分页列表
     *
     * @param query 查询条件
     */
    @Override
    public PageData<SysDictTypeVo> selectDictTypesPage(SysDictTypeQuery query) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_DICT_TYPE.DEFAULT_COLUMNS)
            .where(SYS_DICT_TYPE.NAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())));
        Page<SysDictType> page = mapper.paginate(query.buildPage(), qw);
        return PageData.pageAs(page, (dict) -> converter.convert(dict, SysDictTypeVo.class));
    }

    /**
     * 字典类型表单数据
     *
     * @param id 字典ID
     */
    @Override
    public SysDictTypeVo form(Long id) {
        SysDictType dictType = this.getById(id);
        return converter.convert(dictType, SysDictTypeVo.class);
    }

    /**
     * 新增字典类型
     */
    @Override
    public boolean add(SysDictTypeBo bo) {
        // 校验
        checkCodeUnique(null, bo.getCode());
        // 新增到数据库
        SysDictType dictType = converter.convert(bo, SysDictType.class);
        return this.save(dictType);
    }

    /**
     * 修改字典类型
     *
     * @param bo
     */
    @Override
    public boolean edit(SysDictTypeBo bo) {
        // 校验
        checkCodeUnique(bo.getId(), bo.getCode());
        // 保存到数据库
        SysDictType dictType = converter.convert(bo, SysDictType.class);
        return this.updateById(dictType);
    }

    /**
     * 检查类型编码是否唯一
     *
     * @param dictTypeId 字典类型ID
     * @param dictTypeId 字典类型编码
     */
    private void checkCodeUnique(Long dictTypeId, String code) {
        SysDictType dictType = mapper.selectOneByCondition(SYS_DICT_TYPE.CODE.eq(code));
        AssertUtils.isTrue(
            ObjUtil.isNotNull(dictType) && ObjUtil.notEquals(dictType.getId(), dictTypeId),
            "字典类型编码重复"
        );
    }
}
