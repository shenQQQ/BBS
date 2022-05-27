package indi.shenqqq.bbs;

import indi.shenqqq.bbs.service.IRecommendService;
import indi.shenqqq.bbs.utils.JsonUtils;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BbsApplicationTests {

    @Resource
    IRecommendService service;

    @Test
    void contextLoads() throws SQLException, IOException {
        InetAddress inetAddress  = InetAddress.getByName("119.3.8.118");
        System.out.println(inetAddress.isReachable(500));
    }

}
