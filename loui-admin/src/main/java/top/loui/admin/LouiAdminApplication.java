package top.loui.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;

@MapperScan("top.loui.admin.mapper")
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public class LouiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LouiAdminApplication.class, args);
    }
}
