package top.loui.admin.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.github.linpeilie.annotations.AutoMapping;
import io.github.linpeilie.annotations.AutoMappings;
import lombok.Data;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;
import top.loui.admin.domain.vo.SysUserVo;
import top.loui.admin.domain.vo.UserExportVO;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@AutoMappers({
    @AutoMapper(target = SysUserVo.class, reverseConvertGenerate = false),
    @AutoMapper(target = UserExportVO.class, reverseConvertGenerate = false),
})
@Data
@Table(value = "sys_user")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 5757966956671028218L;

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别((0:未知;1:男;2:女))
     */
    @AutoMappings({
        @AutoMapping(targetClass = SysUserVo.class, target = "genderLabel", expression = "java(top.loui.admin.enums.Gender.getLabelByValue(source.getGender()))"),
        @AutoMapping(targetClass = UserExportVO.class, target = "gender", expression = "java(top.loui.admin.enums.Gender.getLabelByValue(source.getGender()))"),
    })
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    @Column(ignore = true)
    private String deptName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 用户状态((1:正常;0:禁用))
     */
    private Integer status;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 逻辑删除标识(0:未删除;1:已删除)
     */
    @Column(isLogicDelete = true)
    private Boolean deleted;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "NOW()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "NOW()")
    private LocalDateTime updateTime;

}
