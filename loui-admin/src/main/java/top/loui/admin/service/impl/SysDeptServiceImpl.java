package top.loui.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import top.loui.admin.domain.SysDept;
import top.loui.admin.mapper.SysDeptMapper;
import top.loui.admin.service.SysDeptService;
import org.springframework.stereotype.Service;

/**
 * 部门表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

}
