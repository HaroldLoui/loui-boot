package top.loui.admin.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典数据表 实体类。
 *
 * @author hanjinfeng
 * @since 2024-01-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_dict")
public class SysDict implements Serializable {

    @Serial
    private static final long serialVersionUID = -3418855839256355333L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
    private Long id;

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 是否默认(1:是;0:否)
     */
    private Integer defaulted;

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
