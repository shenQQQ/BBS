package indi.shenqqq.bbs.socket;

import com.alibaba.fastjson.JSON;
import indi.shenqqq.bbs.model.dto.WebSocketMessage;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 11:03
 * @Description XX
 */
public class MessageEncoder implements Encoder.Text<WebSocketMessage> {
    @Override
    public String encode(WebSocketMessage o) {
        return JSON.toJSONString(o);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
