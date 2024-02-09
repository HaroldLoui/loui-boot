package top.loui.admin.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 3713360320277979895L;

    private String name;

    private String url;
}
