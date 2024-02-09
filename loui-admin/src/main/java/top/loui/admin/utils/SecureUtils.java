package top.loui.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.crypto.SecureUtil;
import org.dromara.hutool.crypto.asymmetric.KeyType;
import org.dromara.hutool.crypto.asymmetric.RSA;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * 加密解密工具
 *
 * @author hanjinfeng
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecureUtils extends SecureUtil {

    private static final RSA _RSA = SpringUtil.getBean(RSA.class);

    /**
     * 加密字符串
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        return _RSA.encryptBase64(str, KeyType.PrivateKey);
    }

    /**
     * 解密字符串
     *
     * @param str 字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str) {
        return _RSA.decryptStr(str, KeyType.PublicKey);
    }
}
