package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.ArticleMapper;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 13:05
 * @Description XX
 */
@Service
public class ArticleService implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserService userService;

    @Override
    public Article selectById(int id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Article::getId, id);
        return articleMapper.selectOne(wrapper);
    }

    @Override
    public Article selectByTitle(String title) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Article::getTitle, title);
        return articleMapper.selectOne(wrapper);
    }

    @Override
    public Page<Map<String, Object>> selectAll(int pageNo, int pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        page = articleMapper.selectAll(page);
        return page;
    }

    @Override
    public Page<Map<String, Object>> selectByUserId(int userId, int pageNo, int pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        page = articleMapper.selectByUserId(page, userId);
        return page;
    }

    @Override
    public void save(String title, String content, String headImg, User user) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setHeadImg(headImg);
        article.setInTime(new Date());
        article.setUserId(user.getId());
        article.setTop(false);
        article.setGood(false);
        article.setView(1);
        article.setCollectCount(0);
        article.setCommentCount(0);
        articleMapper.insert(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.updateById(article);
    }

    @Override
    public void delete(Article article) {
        Integer id = article.getId();
        // 删除相关通知
        // 删除相关收藏
        // 删除相关的评论;
        // 将话题对应的标签 topicCount -1
        articleMapper.deleteById(id);
    }

    @Override
    public void deleteByUserId(int userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Article::getUserId, userId);
        articleMapper.delete(wrapper);
    }

    @Override
    public int countAll() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        return articleMapper.selectCount(wrapper);
    }


    @Override
    public Page<Map<String, Object>> search(Integer pageNo, Integer pageSize, String keyword) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        return articleMapper.search(page, keyword);
    }
}
