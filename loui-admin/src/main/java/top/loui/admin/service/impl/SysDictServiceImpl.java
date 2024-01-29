package top.loui.admin.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysDict;
import top.loui.admin.domain.bo.SysDictBo;
import top.loui.admin.domain.query.SysDictQuery;
import top.loui.admin.domain.vo.DropdownListVo;
import top.loui.admin.domain.vo.SysDictVo;
import top.loui.admin.mapper.SysDictMapper;
import top.loui.admin.service.SysDictService;

import java.util.List;

import static top.loui.admin.domain.table.SysDictTableDef.SYS_DICT;

/**
 * 字典数据表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final Converter converter;

    /**
     * 字典分页列表
     *
     * @param query 查询条件
     */
    @Override
    public PageData<SysDictVo> selectDictPage(SysDictQuery query) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_DICT.DEFAULT_COLUMNS)
            .where(SYS_DICT.NAME.like(query.getKeywords(), StrUtil.isNotEmpty(query.getKeywords())))
            .and(SYS_DICT.TYPE_CODE.eq(query.getTypeCode(), StrUtil.isNotEmpty(query.getTypeCode())));
        Page<SysDict> page = mapper.paginate(query.buildPage(), qw);
        return PageData.pageAs(page, (dict) -> converter.convert(dict, SysDictVo.class));
    }

    /**
     * 字典下拉列表
     *
     * @param typeCode 字典类型编码
     */
    @Override
    public List<DropdownListVo> options(String typeCode) {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_DICT.ID.as("value"), SYS_DICT.NAME.as("label"))
            .where(SYS_DICT.TYPE_CODE.eq(typeCode));
        return mapper.selectListByQueryAs(qw, DropdownListVo.class);
    }

    /**
     * 字典数据表单数据
     *
     * @param id 字典ID
     */
    @Override
    public SysDictVo form(Long id) {
        SysDict dict = this.getById(id);
        return converter.convert(dict, SysDictVo.class);
    }

    /**
     * 新增字典
     *
     * @param bo
     */
    @Override
    public boolean add(SysDictBo bo) {
        SysDict dict = converter.convert(bo, SysDict.class);
        return this.save(dict);
    }

    /**
     * 修改字典
     *
     * @param bo
     */
    @Override
    public boolean edit(SysDictBo bo) {
        SysDict dict = converter.convert(bo, SysDict.class);
        return this.updateById(dict);
    }
}
