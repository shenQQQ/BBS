package indi.shenqqq.bbs.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author Shen Qi
 * @Date 2022/5/17 18:36
 * @Description XX
 */
@Configuration
public class FlywayConfig {

    @Resource
    private DataSource dataSource;

    @PostConstruct
    @DependsOn("dataSourceHelper")
    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).locations("classpath:db/migration",
                "filesystem:db/migration").baselineOnMigrate(true).load();
        System.out.println(1);
        flyway.migrate();
    }

}
