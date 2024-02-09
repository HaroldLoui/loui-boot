package top.loui.admin.service;


import top.loui.admin.common.page.PageData;
import top.loui.admin.domain.SysConfig;
import com.mybatisflex.core.service.IService;
import top.loui.admin.domain.bo.SysConfigBo;
import top.loui.admin.domain.query.SysConfigQuery;
import top.loui.admin.domain.vo.SysConfigVo;

import java.util.List;

/**
 * 参数配置表 服务层。
 *
 * @author hanjinfeng39
 * @since 1.0
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 根据key查询value
     *
     * @param key 配置键
     * @return value
     */
    String selectValueByConfigKey(String key);

    String putCacheKey(String key);

    /**
     * 配置分页列表
     *
     * @param query 查询条件
     */
    PageData<SysConfigVo> selectConfigPage(SysConfigQuery query);

    /**
     * 系统配置表单数据
     *
     * @param id 系统配置ID
     */
    SysConfigVo form(Long id);

    /**
     * 新增系统配置
     */
    boolean add(SysConfigBo bo);

    /**
     * 修改系统配置
     */
    boolean edit(SysConfigBo bo);

    /**
     * 删除系统配置
     */
    boolean delete(List<Long> ids);
}