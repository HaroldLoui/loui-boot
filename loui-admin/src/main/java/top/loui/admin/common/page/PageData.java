package top.loui.admin.common.page;

import com.mybatisflex.core.paginate.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7477796437832559265L;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 数据总数
     */
    private long total;

    /**
     * 转换Mybatis-Flex的分页对象为自定义的分页对象
     *
     * @param page Mybatis-Flex分页对象
     * @param func 转换方法
     * @param <E>  原Entity对象，泛型
     * @param <Vo> Vo对象，泛型
     * @return 自定义分页对象
     */
    public static <E, Vo> PageData<Vo> pageAs(Page<E> page, Function<E, Vo> func) {
        // 数据总数
        long totalRow = page.getTotalRow();
        if (totalRow <= 0L) {
            return new PageData<>(Collections.emptyList(), 0L);
        }
        // 分页数据
        List<E> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return new PageData<>(Collections.emptyList(), totalRow);
        }
        List<Vo> voList = records.stream().map(func).toList();
        return new PageData<>(voList, totalRow);
    }
}
