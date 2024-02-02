package top.loui.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysConfig;
import top.loui.admin.domain.bo.SysConfigBo;
import top.loui.admin.domain.query.SysConfigQuery;
import top.loui.admin.domain.vo.SysConfigVo;
import top.loui.admin.mapper.SysConfigMapper;
import top.loui.admin.service.SysConfigService;
import top.loui.admin.utils.AssertUtils;
import top.loui.admin.utils.RedisUtils;

import java.util.List;

import static top.loui.admin.domain.table.SysConfigTableDef.SYS_CONFIG;

/**
 * 参数配置表 服务层实现。
 *
 * @author hanjinfeng39
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final Converter converter;

    /**
     * 根据key查询value
     *
     * @param configKey 配置键
     * @return value
     */
    @Override
    public String selectValueByConfigKey(String configKey) {
        final String key = "sys_config:" + configKey;
        if (RedisUtils.hasKey(key)) {
            return RedisUtils.getCacheObject(key);
        }
        QueryWrapper qw = QueryWrapper.create()
                .select(SYS_CONFIG.CONFIG_VALUE)
                .from(SYS_CONFIG)
                .where(SYS_CONFIG.CONFIG_KEY.eq(configKey));
        String value = mapper.selectObjectByQueryAs(qw, String.class);
        if (ObjUtil.isNull(value)) {
            return null;
        }
        RedisUtils.setCacheObject(key, value);
        return value;
    }

    /**
     * 配置分页列表
     *
     * @param query 查询条件
     */
    @Override
    public PageData<SysConfigVo> selectConfigPage(SysConfigQuery query) {
        QueryWrapper qw = QueryWrapper.create()
                .select(SYS_CONFIG.DEFAULT_COLUMNS)
                .from(SYS_CONFIG)
                .where(SYS_CONFIG.CONFIG_NAME.like(query.getKeywords(), StrUtil::isNotEmpty));
        Page<SysConfig> paginate = mapper.paginate(query.buildPage(), qw);
        return PageData.pageAs(paginate, (config) -> converter.convert(config, SysConfigVo.class));
    }

    /**
     * 系统配置表单数据
     *
     * @param id 系统配置ID
     */
    @Override
    public SysConfigVo form(Long id) {
        SysConfig config = this.getById(id);
        return converter.convert(config, SysConfigVo.class);
    }

    /**
     * 新增系统配置
     */
    @Override
    public boolean add(SysConfigBo bo) {
        checkConfigKeyUnique(null, bo.getConfigKey());
        SysConfig config = converter.convert(bo, SysConfig.class);
        config.setCreateBy(StpUtil.getLoginIdAsLong());
        return this.save(config);
    }

    /**
     * 修改系统配置
     */
    @Override
    public boolean edit(SysConfigBo bo) {
        String configKey = bo.getConfigKey();
        checkConfigKeyUnique(bo.getId(), configKey);
        SysConfig config = converter.convert(bo, SysConfig.class);
        config.setUpdateBy(StpUtil.getLoginIdAsLong());
        boolean result = this.updateById(config);
        if (result) {
            final String key = "sys_config:" + configKey;
            RedisUtils.setCacheObject(key, config.getConfigValue());
        }
        return result;
    }

    /**
     * 删除系统配置
     */
    @Override
    public boolean delete(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        QueryWrapper qw = QueryWrapper.create()
                .select(SYS_CONFIG.CONFIG_KEY)
                .from(SYS_CONFIG)
                .where(SYS_CONFIG.ID.in(ids));
        List<String> configKeys = mapper.selectListByQueryAs(qw, String.class);
        boolean result = this.removeByIds(ids);
        if (result) {
            List<String> keys = configKeys.stream().map(key -> "sys_config:" + key).toList();
            RedisUtils.delete(keys);
        }
        return result;
    }

    private void checkConfigKeyUnique(Long configId, String configKey) {
        QueryWrapper qw = QueryWrapper.create()
                .select(SYS_CONFIG.DEFAULT_COLUMNS)
                .from(SYS_CONFIG)
                .where(SYS_CONFIG.CONFIG_KEY.eq(configKey));
        SysConfig sysConfig = mapper.selectOneByQuery(qw);
        AssertUtils.isTrue(
                ObjUtil.isNotNull(sysConfig) && ObjUtil.notEquals(sysConfig.getId(), configId),
                "参数键名重复"
        );
    }
}