package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.service.impl.ArticleTagService;
import indi.shenqqq.bbs.service.impl.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author Shen Qi
 * @Date 2022/4/12 14:07
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTagServiceTest {

    @Resource
    ArticleTagService articleTagService;
    @Resource
    TagService tagService;

    @Test
    public void insertTest(){
        articleTagService.insertArticleTag(1,tagService.insertTag("游戏,技术,动漫,纪录片,开源"));
        articleTagService.insertArticleTag(2,tagService.insertTag("游戏,技术,动漫,纪录片"));
        articleTagService.insertArticleTag(3,tagService.insertTag("游戏,技术,动漫"));
        articleTagService.insertArticleTag(4,tagService.insertTag("游戏,技术"));
        articleTagService.insertArticleTag(5,tagService.insertTag("游戏"));
    }
}
