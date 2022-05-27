package indi.shenqqq.bbs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author Shen Qi
 * @Date 2022/4/21 15:32
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemConfigTest {

    @Resource
    ISystemConfigService systemConfigService;

    @Test
    public void SystemConfigTest(){
        System.out.println(systemConfigService.selectAllConfig());
        String host = systemConfigService.selectByKey("redis_host");
        String port = systemConfigService.selectByKey("redis_port");
        String password = StringUtils.isEmpty(systemConfigService.selectByKey("redis_password")) ? null : systemConfigService.selectByKey("redis_password");
        String database = systemConfigService.selectByKey("redis_database");
        String timeout = systemConfigService.selectByKey("redis_timeout");
        System.out.println(host);
        System.out.println(port);
        System.out.println(password);
        System.out.println(database);
        System.out.println(timeout);
    }
}
