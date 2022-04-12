package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.ArticleTag;
import indi.shenqqq.bbs.model.Tag;

import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:25
 * @Description XX
 */
public interface IArticleTagService {
    List<ArticleTag> selectByArticleId(Integer articleId);

    List<ArticleTag> selectByTagId(Integer tagId);

    void insertArticleTag(Integer articleId, List<Tag> tagList);

    void deleteByArticleId(Integer id);

    void updateTagByArticleId(Integer articleId, List<Tag> tagList);
}
