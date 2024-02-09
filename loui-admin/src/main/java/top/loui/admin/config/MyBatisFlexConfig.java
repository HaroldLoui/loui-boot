package top.loui.admin.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;

@Slf4j
@Configuration
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        // globalConfig.setLogImpl(StdOutImpl.class);
        // 开启sql审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(am ->
            log.info("{}; ==> 耗时: {}ms", am.getFullSql(), am.getElapsedTime())
        );
        // 注册自定义的主键生成策略
        KeyGeneratorFactory.register(MySnowFlakeIdGenerator.KEY, new MySnowFlakeIdGenerator());
    }

}