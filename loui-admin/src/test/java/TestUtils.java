import org.dromara.hutool.crypto.asymmetric.KeyType;
import org.dromara.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;

public class TestUtils {

    @Test
    public void testRsa() {
        RSA rsa = new RSA();
        System.out.println("private: " + rsa.getPrivateKeyBase64());
        System.out.println("public: " + rsa.getPublicKeyBase64());
        String encrypt = rsa.encryptBase64("123456", KeyType.PrivateKey);
        System.out.println(encrypt.length() + ", " + encrypt);
        System.out.println(rsa.decryptStr(encrypt, KeyType.PublicKey));
    }
}
