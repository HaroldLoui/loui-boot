package top.loui.admin.domain;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import top.loui.admin.config.id.MySnowFlakeIdGenerator;
import top.loui.admin.domain.vo.SysConfigVo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 参数配置表 实体类。
 *
 * @author hanjinfeng39
 * @since 1.0
 */
@AutoMapper(target = SysConfigVo.class, reverseConvertGenerate = false)
@Data
@Table(value = "sys_config")
public class SysConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 8195190439212787652L;

    /**
     * 参数主键
     */
    @Id(keyType = KeyType.Generator, value = MySnowFlakeIdGenerator.KEY)
    private Long id;

    /**
     * 参数名称
     */
    @Column(value = "config_name")
    private String configName;

    /**
     * 参数键名
     */
    @Column(value = "config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @Column(value = "config_value")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Column(value = "config_type")
    private String configType;

    /**
     * 创建者
     */
    @Column(value = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(value = "create_time", onInsertValue = "NOW()")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Column(value = "update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @Column(value = "update_time", onUpdateValue = "NOW()")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;
}
