package indi.shenqqq.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "indi.shenqqq.bbs",
        exclude = {DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
public class BBSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BBSApplication.class, args);
    }

}
