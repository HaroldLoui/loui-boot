package top.loui.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import top.loui.admin.domain.SysUserRole;
import top.loui.admin.mapper.SysUserRoleMapper;
import top.loui.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
