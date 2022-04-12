package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.ArticleMapper;
import indi.shenqqq.bbs.dao.CollectMapper;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.ICollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 14:38
 * @Description XX
 */
@Service
@Transactional
public class CollectService implements ICollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private ArticleService articleService;

    @Override
    public Collect selectByUserIdAndArticleId(Integer userId, Integer articleId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getArticleId, articleId).eq(Collect::getUserId, userId);
        List<Collect> collects = collectMapper.selectList(wrapper);
        if (collects.size() > 0) return collects.get(0);
        return null;
    }

    @Override
    public List<Collect> selectByArticleId(Integer articleId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getArticleId, articleId);
        return collectMapper.selectList(wrapper);
    }

    @Override
    public Page<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        page = collectMapper.selectByUserId(page, userId);
        return page;
    }

    @Override
    public void save(Integer articleId, User user) {
        Collect collect = new Collect();
        collect.setArticleId(articleId);
        collect.setUserId(user.getId());
        collect.setInTime(new Date());
        collectMapper.insert(collect);

        Article article = articleService.selectById(articleId);
        article.setCollectCount(article.getCollectCount() + 1);
        articleService.update(article);
    }

    @Override
    public void delete(Integer articleId, Integer userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getArticleId, articleId).eq(Collect::getUserId, userId);
        collectMapper.delete(wrapper);
        // 对话题中冗余的collectCount字段收藏数量-1
        Article article = articleService.selectById(articleId);
        article.setCollectCount(article.getCollectCount() - 1);
        articleService.update(article);
    }

    @Override
    public void deleteByArticleId(Integer articleId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getArticleId, articleId);
        collectMapper.delete(wrapper);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getUserId, userId);
        collectMapper.delete(wrapper);
    }

    @Override
    public int countByUserId(Integer userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getUserId, userId);
        return collectMapper.selectCount(wrapper);
    }
}
