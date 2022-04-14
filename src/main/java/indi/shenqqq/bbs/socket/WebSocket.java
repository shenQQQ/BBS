package indi.shenqqq.bbs.socket;

import indi.shenqqq.bbs.model.dto.WebSocketMessage;
import indi.shenqqq.bbs.model.vo.UserWithWebSocketVO;
import indi.shenqqq.bbs.service.INotificationService;
import indi.shenqqq.bbs.service.impl.NotificationService;
import indi.shenqqq.bbs.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 11:02
 * @Description XX
 */
@ServerEndpoint(value = "/websocket", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@Component
@Slf4j
public class WebSocket {

    private final static AtomicInteger online = new AtomicInteger(0);
    public static Map<Session, UserWithWebSocketVO> webSockets = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        online.incrementAndGet();
        webSockets.put(session, new UserWithWebSocketVO());
    }

    @OnClose
    public void onClose(Session session) {
        online.decrementAndGet();
        webSockets.remove(session);
    }

    @OnMessage
    public void onMessage(WebSocketMessage message, Session session) {
        if (message != null) {
            switch (message.getType()) {
                case "bind":
                    bind(message, session);
                    break;
                case "notReadCount":
                    fetchNotReadCount(message, session);
                    break;
                default:
                    break;
            }
        }
    }

    private void bind(WebSocketMessage message, Session session) {
        try {
            Integer userId = Integer.parseInt(((Map) (message.getPayload())).get("userId").toString());
            String username = ((Map) (message.getPayload())).get("username").toString();
            UserWithWebSocketVO userWithWebSocketVO = webSockets.get(session);
            userWithWebSocketVO.setUserId(userId);
            userWithWebSocketVO.setUsername(username);
            webSockets.put(session, userWithWebSocketVO);
            session.getBasicRemote().sendObject(new WebSocketMessage("bind", null));
            fetchNotReadCount(message, session);
        } catch (IOException | EncodeException e) {
            log.error("发送ws消息失败, 异常信息: {}", e.getMessage());
        }
    }

    public static void fetchNotReadCount(WebSocketMessage message, Session session) {
        try {
            INotificationService notificationService = SpringContextUtils.getBean(NotificationService.class);
            long countNotRead = notificationService.countNotRead(WebSocket.webSockets.get(session).getUserId());
            session.getBasicRemote().sendObject(new WebSocketMessage("notification_notread", countNotRead));
        } catch (IOException | EncodeException e) {
            log.error("发送ws消息失败, 异常信息: {}", e.getMessage());
        }
    }

    private static Session selectSessionByUserId(Integer userId) {
        return webSockets.entrySet().stream().filter(x -> x.getValue().getUserId().equals(userId)).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public static void sendMessage(Integer userId, WebSocketMessage message) {
        try {
            Session session = selectSessionByUserId(userId);
            if (session != null) {
                session.getBasicRemote().sendObject(message);
                log.info("发送成功");
            }
            else{
                log.info("没法出去");
            }
            fetchNotReadCount(message,session);
        } catch (IOException | EncodeException e) {
            log.error("发送ws消息失败, 异常信息：{}", e.getMessage());
        }
    }

}