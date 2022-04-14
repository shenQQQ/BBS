package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/3 9:01
 * @Description XX
 */
public interface INotificationService {
    // 查询消息
    List<Map<String, Object>> selectByUserId(Integer userId, Boolean read, Integer limit);

    Page<Map<String, Object>> selectAll(int pageNo, int pageSize,int userId);

    void markRead(Integer userId);

    // 查询未读消息数量
    long countNotRead(Integer userId);

    void deleteByArticleId(Integer articleId);

    void deleteByUserId(Integer userId);

    void save(Integer userId, Integer targetUserId, Integer articleId, String action, String content);

}
