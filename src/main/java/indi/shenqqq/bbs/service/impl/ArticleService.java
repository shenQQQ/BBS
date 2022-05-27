package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.ArticleMapper;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import org.apache.kafka.common.internals.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 13:05
 * @Description XX
 */
@Service
@Transactional
public class ArticleService implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private CollectService collectService;
    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private IndexService indexService;

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
        indexService.indexArticle(String.valueOf(article.getId()), article.getTitle(), article.getContent(),article.getHeadImg());
        articleMapper.insert(article);
    }

    @Override
    public void update(Article article) {
        indexService.indexArticle(String.valueOf(article.getId()), article.getTitle(), article.getContent(),article.getHeadImg());
        articleMapper.updateById(article);
    }

    @Override
    public void delete(Article article) {
        Integer id = article.getId();
        // 删除相关通知
        articleTagService.deleteByArticleId(article.getId());
        collectService.deleteByArticleId(article.getId());
        commentService.deleteByArticleId(article.getId());
        indexService.deleteArticleIndex(String.valueOf(article.getId()));
        articleMapper.deleteById(id);
    }

    // 根据用户id删除帖子
    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Article::getUserId, userId);
        List<Article> articles = articleMapper.selectList(wrapper);
        articles.forEach(article -> {
            indexService.deleteArticleIndex(String.valueOf(article.getId()));
            commentService.deleteByArticleId(article.getId());
            collectService.deleteByArticleId(article.getId());
            articleTagService.deleteByArticleId(article.getId());
        });
        //删除话题
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
