package indi.shenqqq.bbs;

import indi.shenqqq.bbs.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
class BbsApplicationTests {

    @Test
    void contextLoads() throws SQLException {
        List<Map<String,String>> list = new LinkedList<>();
        Map<String,String> map1 = new LinkedHashMap<>();
        map1.put("key","/game");
        map1.put("title","游戏");
        Map<String,String> map2 = new LinkedHashMap<>();
        map2.put("key","/user");
        map2.put("title","用户管理");
        list.add(map1);
        list.add(map2);
        System.out.println(JsonUtils.objectToJson(list));
    }

}
