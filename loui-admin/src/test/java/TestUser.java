import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.github.linpeilie.Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.loui.admin.LouiAdminApplication;
import top.loui.admin.domain.SysMenu;
import top.loui.admin.domain.SysRole;
import top.loui.admin.domain.SysUser;
import top.loui.admin.domain.bo.SysMenuBo;
import top.loui.admin.domain.vo.menu.SysMenuTableVo.Type;
import top.loui.admin.mapper.SysRoleMapper;
import top.loui.admin.service.SysRoleService;
import top.loui.admin.service.SysUserService;
import top.loui.admin.utils.JsonUtils;
import top.loui.admin.utils.RedisUtils;

import java.util.Set;

import static top.loui.admin.domain.table.SysRoleTableDef.SYS_ROLE;

@DisplayName("系统用户 单元测试")
@SpringBootTest(classes = LouiAdminApplication.class)
public class TestUser {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleService roleService;

    @Autowired
    private Converter converter;

    @DisplayName("新增 系统用户")
    @Test
    public void testInsert() {
        SysUser entity = new SysUser();
        entity.setUsername("admin");
        entity.setPassword("123456");
        if (userService.save(entity)) {
            System.out.println(entity.getId());
        }
    }

    @DisplayName("修改 系统用户")
    @Test
    public void testUpdate() {
    }

    @DisplayName("查询 系统用户")
    @Test
    public void testQuery() {
        QueryWrapper qw = QueryWrapper.create()
            .select(SYS_ROLE.DEFAULT_COLUMNS);
        Page<SysRole> pageQuery = Page.of(1, 10);
        pageQuery.setOptimizeCountQuery(true);
        Page<SysRole> page = roleMapper.paginate(pageQuery, qw);
        System.out.println(JsonUtils.toJsonString(page));
    }

    @DisplayName("测试 Redis Keys")
    @Test
    public void testRedisKeys() {
        Set<String> keys = RedisUtils.keys("sa-token:roles*");
        System.out.println(keys);
    }

    @DisplayName("测试 Menu")
    @Test
    public void testMenu() {
        SysMenuBo menuBo = new SysMenuBo();
        menuBo.setId(0L);
        menuBo.setParentId(0L);
        menuBo.setName("123");
        menuBo.setPath("123");
        menuBo.setIcon("213");
        menuBo.setComponent("321");
        menuBo.setRedirect("132");
        menuBo.setPerm("123");
        menuBo.setSort(0);
        menuBo.setType(Type.BUTTON);
        menuBo.setVisible(0);
        SysMenu menu = converter.convert(menuBo, SysMenu.class);
        System.out.println(JsonUtils.toJsonString(menu));
    }
}
