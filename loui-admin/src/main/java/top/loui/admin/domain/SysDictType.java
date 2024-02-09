package top.loui.admin.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;
import top.loui.admin.domain.vo.SysDictTypeVo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@AutoMapper(target = SysDictTypeVo.class, reverseConvertGenerate = false)
@Data
@Table(value = "sys_dict_type")
public class SysDictType implements Serializable {

    @Serial
    private static final long serialVersionUID = 8622736386296772756L;

    /**
     * 主键 
     */
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型编码
     */
    private String code;

    /**
     * 状态(0:正常;1:禁用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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
