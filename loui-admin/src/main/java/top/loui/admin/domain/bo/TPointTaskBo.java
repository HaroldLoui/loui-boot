package top.loui.admin.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import top.loui.admin.domain.TPointTask;

import java.io.Serial;
import java.io.Serializable;

/**
 * 积分任务 实体类。
 *
 * @author hanjinfeng
 * @since 2024-04-23
 */
@AutoMapper(target = TPointTask.class, reverseConvertGenerate = false)
@Data
public class TPointTaskBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    /**
     * 任务类型
     */
    @NotNull(message = "任务名称不能为空")
    private Integer taskType;

    /**
     * 积分数
     */
    @NotNull(message = "积分数不能为空")
    @Range(min = 10, max = 300, message = "积分数区间为[10-300]")
    private Integer pointNumber;


    /**
     * 备注
     */
    private String remark;

}