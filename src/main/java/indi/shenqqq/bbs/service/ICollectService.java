package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 14:25
 * @Description XX
 */
public interface ICollectService {

    //查找某人是不是收藏过这篇文章
    Collect selectByUserIdAndArticleId(Integer userId, Integer articleId);

    List<Collect> selectByArticleId(Integer articleId);

    Page<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize);

    void save(Integer articleId, User user);

    void delete(Integer articleId, Integer userId);

    void deleteByArticleId(Integer articleId);

    void deleteByUserId(Integer userId);

    // 查询用户收藏的话题数
    int countByUserId(Integer userId);

}
