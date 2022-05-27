package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.NotificationMapper;
import indi.shenqqq.bbs.model.Notification;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.dto.WebSocketMessage;
import indi.shenqqq.bbs.mq.Consumer;
import indi.shenqqq.bbs.mq.Producer;
import indi.shenqqq.bbs.service.INotificationService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.socket.WebSocket;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/3 9:02
 * @Description XX
 */

@Service
@Transactional
public class NotificationService implements INotificationService {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private IUserService userService;
    @Resource
    private Producer producer;
    @Resource
    private Consumer consumer;

    @Override
    public List<Map<String, Object>> selectByUserId(Integer userId, Boolean read, Integer limit) {
        List<Map<String, Object>> notifications = notificationMapper.selectByUserId(userId, read, limit);
        return notifications;
    }

    @Override
    public Page<Map<String, Object>> selectAll(int pageNo, int pageSize, int userId) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        page = notificationMapper.selectAll(page, userId);
        return page;
    }

    @Override
    public void markRead(Integer userId) {
        notificationMapper.updateNotificationStatus(userId);
        WebSocket.sendMessage(userId, new WebSocketMessage("notification_notread", 0));
    }

    @Override
    public long countNotRead(Integer userId) {
        return notificationMapper.countNotRead(userId);
    }

    @Override
    public void deleteByArticleId(Integer articleId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getArticleId, articleId);
        notificationMapper.delete(wrapper);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getTargetUserId, userId).or().eq(Notification::getUserId, userId);
        notificationMapper.delete(wrapper);
    }

    @Override
    public void save(Integer userId, Integer targetUserId, Integer articleId, String action, String content) {
        Notification notification = new Notification();
        notification.setAction(action);
        notification.setContent(content);
        notification.setUserId(userId);
        notification.setTargetUserId(targetUserId);
        notification.setArticleId(articleId);
        notification.setInTime(new Date());
        notification.setIsread(false);
        notificationMapper.insert(notification);
    }

    @Override
    public void broadcast(String content) {
        int count = producer.broadcast(content);
        consumer.receive(count);
    }

}
