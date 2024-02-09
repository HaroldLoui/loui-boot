package top.loui.admin.domain.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysDeptQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -57974844472291405L;

    /**
     * 关键字(部门名称)
     */
    private String keywords;

    /**
     * 状态(1->正常；0->禁用)
     */
    private Integer status;
}