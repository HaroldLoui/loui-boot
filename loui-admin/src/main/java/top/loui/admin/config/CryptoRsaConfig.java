package top.loui.admin.config;

import org.dromara.hutool.crypto.asymmetric.RSA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoRsaConfig {

    @Value("${crypto.rsa.pub}")
    private String pub;

    @Value("${crypto.rsa.pri}")
    private String pri;

    @Bean
    public RSA rsa() {
        return new RSA(pri, pub);
    }
}
