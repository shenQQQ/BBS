package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.dto.WebSocketMessage;
import indi.shenqqq.bbs.socket.WebSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 14:59
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {

    @Test
    public void sendMessageTest(){
        WebSocket.sendMessage(1,new WebSocketMessage("hello","hello"));
    }

}
