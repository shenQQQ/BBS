package indi.shenqqq.bbs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import indi.shenqqq.bbs.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author Shen Qi
 * @Date 2022/5/17 18:30
 * @Description XX
 */
@Configuration
public class DataSourceConfig {

    @Resource
    private SiteConfig siteConfig;

    private HikariDataSource dataSource;

    public HikariDataSource instance() {
        if (siteConfig == null) siteConfig = SpringContextUtils.getBean(SiteConfig.class);
        if (dataSource != null) return dataSource;
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(siteConfig.getDatasource_driver());
        config.setJdbcUrl(siteConfig.getDatasource_url());
        config.setUsername(siteConfig.getDatasource_username());
        config.setPassword(siteConfig.getDatasource_password());
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 500);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        //池中最小空闲链接数量
        config.setMinimumIdle(10);
        //池中最大链接数量
        config.setMaximumPoolSize(50);
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @DependsOn("dataSourceHelper")
    public DataSource dataSource() {
        return instance();
    }
}

