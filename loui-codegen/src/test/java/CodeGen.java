import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.IDialect;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("生成代码")
public class CodeGen {

    @Test
    @DisplayName("生成代码")
    public void test1() {
        //配置数据源
        try (HikariDataSource dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/market?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&autoReconnect=true&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");

            //创建配置内容，两种风格都可以。
            GlobalConfig globalConfig = createGlobalConfigUseStyle1();
            //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

            //通过 datasource 和 globalConfig 创建代码生成器
            Generator generator = new Generator(dataSource, globalConfig, IDialect.MYSQL);

            //生成代码
            generator.generate();
        }
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setAuthor("hanjinfeng");

        //设置根包
        globalConfig.setBasePackage("top.loui.flex");
        globalConfig.setEntityPackage("top.loui.flex.domain");
        globalConfig.setMapperPackage("top.loui.flex.mapper");
        globalConfig.setServicePackage("top.loui.flex.service");
        globalConfig.setServiceImplPackage("top.loui.flex.service.impl");
        globalConfig.setControllerPackage("top.loui.flex.controller");

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("market_");
        globalConfig.setGenerateTable(
            "market_user_intention",
            "market_user_intention_msg",
            "market_user_intention_msg_card"
        );

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setServiceGenerateEnable(true);
        globalConfig.setServiceImplGenerateEnable(true);
        globalConfig.setControllerGenerateEnable(true);
        globalConfig.setMapperXmlGenerateEnable(true);

        globalConfig.setTableDefGenerateEnable(true);

        //可以单独配置某个列
        // ColumnConfig columnConfig = new ColumnConfig();
        // columnConfig.setColumnName("tenant_id");
        // columnConfig.setLarge(true);
        // columnConfig.setVersion(true);
        // globalConfig.setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}
