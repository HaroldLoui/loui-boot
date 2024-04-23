package top.loui.codegen;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.IDialect;
import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import java.util.Map;

public class CodeGen {

    public static void main(String[] args) {
        //配置数据源
        try (HikariDataSource dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/loui_admin?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&autoReconnect=true&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");
            // dataSource.setDriverClassName("");

            //创建配置内容，两种风格都可以。
            GlobalConfig globalConfig = createGlobalConfigUseStyle1();
            //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

            //通过 datasource 和 globalConfig 创建代码生成器
            Generator generator = new Generator(dataSource, globalConfig, IDialect.MYSQL);

            //生成代码
            generator.generate();
        }
    }

    private static final String BASE_PATH = "C:\\Devlopment\\workspace\\IdeaProjects\\loui-boot\\loui-admin\\src\\main\\";

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setAuthor("hanjinfeng");

        //设置根包
        globalConfig.setSourceDir(BASE_PATH + "java");
        globalConfig.setBasePackage("top.loui.admin");
        globalConfig.setEntityPackage("top.loui.admin.domain");
        globalConfig.setMapperPackage("top.loui.admin.mapper");
        globalConfig.setServicePackage("top.loui.admin.service");
        globalConfig.setServiceImplPackage("top.loui.admin.service.impl");
        globalConfig.setMapperXmlPath(BASE_PATH + "resources\\mapper");
        // globalConfig.setControllerPackage("top.loui.admin.controller");

        //设置表前缀和只生成哪些表
        // globalConfig.setTablePrefix("market_");
        final String[] tableNames = {
            "t_point_task"
            // "sys_dept"
            // ,"sys_dict"
            // ,"sys_dict_type"
            // ,"sys_menu"
            // ,"sys_role"
            // ,"sys_role_menu"
            // ,"sys_user"
            // ,"sys_user_role"
        };
        globalConfig.setGenerateTable(tableNames);

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityOverwriteEnable(true);
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);

        //设置生成 mapper
        globalConfig.setMapperOverwriteEnable(true);
        globalConfig.setMapperGenerateEnable(true);
        // 生成Service
        globalConfig.setServiceGenerateEnable(true);
        // 生成ServiceImpl
        globalConfig.setServiceImplGenerateEnable(true);
        // 生成Controller（不好用）
        // globalConfig.setControllerGenerateEnable(true);
        // 生成Mapper Xml文件
        globalConfig.setMapperXmlOverwriteEnable(true);
        globalConfig.setMapperXmlGenerateEnable(true);
        // 生成TableRef
        globalConfig.setTableDefOverwriteEnable(true);
        globalConfig.setTableDefGenerateEnable(true);

        globalConfig.setEntityJdkVersion(17);

        //可以单独配置某个列
        Map<String, ColumnConfig> map = new HashMap<>();
        // ColumnConfig logicDeleteConfig = new ColumnConfig();
        // logicDeleteConfig.setColumnName("deleted");
        // logicDeleteConfig.setLogicDelete(true);
        // map.put("deleted", logicDeleteConfig);

        ColumnConfig createTimeConfig = new ColumnConfig();
        createTimeConfig.setColumnName("create_time");
        createTimeConfig.setOnInsertValue("NOW()");
        map.put("create_time", createTimeConfig);

        ColumnConfig updateTimeConfig = new ColumnConfig();
        updateTimeConfig.setColumnName("update_time");
        updateTimeConfig.setOnUpdateValue("NOW()");
        map.put("update_time", updateTimeConfig);

        ColumnConfig idConfig = new ColumnConfig();
        idConfig.setColumnName("id");
        idConfig.setKeyType(KeyType.Generator);
        idConfig.setKeyValue(MySnowFlakeIdGenerator.KEY);
        idConfig.setPrimaryKey(true);
        map.put("id", idConfig);

        globalConfig.setColumnConfigMap(map);
        return globalConfig;
    }
}
