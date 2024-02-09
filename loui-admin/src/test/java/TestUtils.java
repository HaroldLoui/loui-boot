import org.dromara.hutool.crypto.asymmetric.KeyType;
import org.dromara.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;
import top.loui.admin.domain.SysMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @Test
    public void testSorted() {
        List<SysMenu> list = new ArrayList<>();
        list.add(getMenu("菜单2", 2));
        list.add(getMenu("菜单1", 1));
        list.add(getMenu("菜单4", 4));
        list.add(getMenu("菜单3", 3));
        list.stream()
            .sorted(Comparator.comparing(SysMenu::getSort))
            .forEach((menu) -> {
                System.out.println(menu.getName() + "," + menu.getSort());
            });
    }

    private SysMenu getMenu(String name, Integer sort) {
        SysMenu menu = new SysMenu();
        menu.setName(name);
        menu.setSort(sort);
        return menu;
    }
}
