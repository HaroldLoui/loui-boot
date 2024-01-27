package top.loui.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import top.loui.admin.domain.SysDictType;
import top.loui.admin.mapper.SysDictTypeMapper;
import top.loui.admin.service.SysDictTypeService;
import org.springframework.stereotype.Service;

/**
 * 字典类型表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

}
