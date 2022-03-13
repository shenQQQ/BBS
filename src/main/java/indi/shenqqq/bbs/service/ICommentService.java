package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 15:17
 * @Description XX
 */
public interface ICommentService {

    List<Comment> selectByArticleId(Integer articleId);

    void deleteByArticleId(Integer articleId);

    void deleteByUserId(Integer userId);

    void save(Comment comment, Article article, User user);

    void save(int userId, int articleId, String content, Article article, User user);

    Comment selectById(Integer id);

    void update(Comment comment);

    void delete(Comment comment);

    Page<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize);

}
