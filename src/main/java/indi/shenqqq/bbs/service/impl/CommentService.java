package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.CommentMapper;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.ICommentService;
import indi.shenqqq.bbs.service.IUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 15:18
 * @Description XX
 */
public class CommentService implements ICommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    @Lazy
    private IArticleService articleService;
    @Resource
    @Lazy
    private IUserService userService;

    @Override
    public List<Comment> selectByArticleId(Integer articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Comment::getArticleId, articleId);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public void deleteByArticleId(Integer articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Comment::getArticleId, articleId);
        commentMapper.delete(wrapper);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Comment::getUserId, userId);
        commentMapper.delete(wrapper);
    }

    @Override
    public void save(Comment comment, Article article, User user) {
        commentMapper.insert(comment);

        // 话题的评论数+1
        article.setCommentCount(article.getCommentCount() + 1);
        articleService.update(article);

        // 增加用户积分
        user.setScore(user.getScore() + 5);
        userService.update(user);

        // 通知
        // 给评论的作者发通知
        // 给话题作者发通知
    }

    @Override
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    @Override
    public void delete(Comment comment) {
        if (comment != null) {
            // 话题评论数-1
            Article article = articleService.selectById(comment.getArticleId());
            article.setCommentCount(article.getCommentCount() - 1);
            articleService.update(article);
            // 减去用户积分
            User user = userService.selectById(comment.getUserId());
            user.setScore(user.getScore() - 5);
            userService.update(user);
            // 删除评论
            commentMapper.deleteById(comment.getId());
        }
    }

    @Override
    public Page<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Page<Map<String, Object>> iPage = new Page<>(pageNo, pageSize);
        Page<Map<String, Object>> page = commentMapper.selectByUserId(iPage, userId);
        return page;
    }
}
