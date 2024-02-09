package top.loui.admin.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 509428188590708697L;

    /**
     * 是否删除
     */
    @Column(value = "del_flag", isLogicDelete = true)
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "create_time", onInsertValue = "NOW()")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(value = "update_time", onUpdateValue = "NOW()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Column(value = "remarks")
    private String remarks;
}
