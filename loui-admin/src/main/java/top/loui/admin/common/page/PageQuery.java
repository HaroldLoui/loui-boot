package top.loui.admin.common.page;

import com.mybatisflex.core.paginate.Page;
import lombok.Data;
import org.dromara.hutool.core.util.ObjUtil;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -8409345415726848226L;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    public <T> Page<T> buildPage() {
        Integer pageNumber = ObjUtil.defaultIfNull(getPageNum(), 1);
        pageNumber = Math.max(pageNumber, 1);
        Integer pageSize1 = ObjUtil.defaultIfNull(getPageSize(), Integer.MAX_VALUE);
        if (pageSize1 < 0) {
            pageSize1 = Integer.MAX_VALUE;
        }
        return Page.of(pageNumber, pageSize1);
    }

}
