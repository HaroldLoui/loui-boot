package top.loui.codegen;

import com.mybatisflex.core.keygen.IKeyGenerator;
import org.dromara.hutool.core.data.id.IdUtil;

public class MySnowFlakeIdGenerator implements IKeyGenerator {

    public static final String KEY = "MySnowFlakeId";

    @Override
    public Object generate(Object entity, String keyColumn) {
        return IdUtil.getSnowflakeNextIdStr();
    }
}
