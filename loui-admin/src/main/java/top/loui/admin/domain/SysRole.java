package top.loui.admin.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;
import top.loui.admin.domain.vo.SysRoleVo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@AutoMapper(target = SysRoleVo.class, reverseConvertGenerate = false)
@Data
@Table(value = "sys_role")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 7036605298281895927L;

    /**
     * 角色ID
     */
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态(1-正常；0-停用)
     */
    private Integer status;

    /**
     * 数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)
     */
    private Integer dataScope;

    /**
     * 逻辑删除标识(0-未删除；1-已删除)
     */
    @Column(isLogicDelete = true)
    private Boolean deleted;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "NOW()")
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @Column(onUpdateValue = "NOW()")
    private LocalDateTime updateTime;

}
