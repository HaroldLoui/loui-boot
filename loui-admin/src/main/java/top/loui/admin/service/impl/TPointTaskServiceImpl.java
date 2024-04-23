package top.loui.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import top.loui.admin.domain.TPointTask;
import top.loui.admin.mapper.TPointTaskMapper;
import top.loui.admin.service.TPointTaskService;
import org.springframework.stereotype.Service;

/**
 * 积分任务 服务层实现。
 *
 * @author hanjinfeng
 * @since 2024-04-23
 */
@Service
public class TPointTaskServiceImpl extends ServiceImpl<TPointTaskMapper, TPointTask> implements TPointTaskService {

}
