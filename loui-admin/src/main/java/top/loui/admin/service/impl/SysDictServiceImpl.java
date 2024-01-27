package top.loui.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import top.loui.admin.domain.SysDict;
import top.loui.admin.mapper.SysDictMapper;
import top.loui.admin.service.SysDictService;
import org.springframework.stereotype.Service;

/**
 * 字典数据表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
