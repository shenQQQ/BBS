package indi.shenqqq.bbs.socket;

import com.alibaba.fastjson.JSON;
import indi.shenqqq.bbs.model.dto.WebSocketMessage;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 11:03
 * @Description XX
 */
public class MessageDecoder implements Decoder.Text<WebSocketMessage> {
    @Override
    public WebSocketMessage decode(String s) {
        return JSON.parseObject(s, WebSocketMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        // 验证json字符串是否合法，合法才会进入decode()方法进行转换，不合法直接抛异常
        return JSON.isValid(s);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}