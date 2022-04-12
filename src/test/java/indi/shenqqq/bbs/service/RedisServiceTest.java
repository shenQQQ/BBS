package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.plugin.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 13:41
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Test
    public void RedisGetStringTest(){
        RedisService redisService = new RedisService();
        String ans = redisService.getString("hello");
        System.out.println(ans);
    }
}
