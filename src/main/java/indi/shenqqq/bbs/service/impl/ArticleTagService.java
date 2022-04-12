package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.shenqqq.bbs.dao.ArticleTagMapper;
import indi.shenqqq.bbs.model.ArticleTag;
import indi.shenqqq.bbs.model.Tag;
import indi.shenqqq.bbs.service.IArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:41
 * @Description XX
 */
@Service
@Transactional
public class ArticleTagService implements IArticleTagService {
    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> selectByArticleId(Integer articleId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ArticleTag::getArticleId, articleId);
        return articleTagMapper.selectList(wrapper);
    }

    @Override
    public List<ArticleTag> selectByTagId(Integer tagId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ArticleTag::getTagId, tagId);
        return articleTagMapper.selectList(wrapper);
    }

    @Override
    public void insertArticleTag(Integer articleId, List<Tag> tagList) {
        this.deleteByArticleId(articleId);
        tagList.forEach(tag -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            articleTagMapper.insert(articleTag);
        });
    }

    @Override
    public void deleteByArticleId(Integer id) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ArticleTag::getArticleId, id);
        articleTagMapper.delete(wrapper);
    }

    @Override
    public void updateTagByArticleId(Integer articleId, List<Tag> tagList) {
        this.deleteByArticleId(articleId);
        this.insertArticleTag(articleId,tagList);
    }


}
