package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.impl.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 13:51
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleServiceTest {

    @Resource
    IArticleService articleService;

    private static Article article = new Article();
    static {
        article.setId(1);
        article.setTitle("兴趣使然的英雄");
        article.setContent("连续普通拳！");
        article.setUserId(1);
    }

    @Test
    public void selectByIdTest(){
        System.out.println(articleService.selectById(1));
    }

    @Test
    public void selectByTitleTest(){
        System.out.println(articleService.selectByTitle(article.getTitle()));
    }

    @Test
    public void selectByUserIdTest(){
        System.out.println(articleService.selectByUserId(1, 1, 5).getRecords());
    }

    @Test
    public void selectAllTest(){
        System.out.println(articleService.selectAll(1, 5).getRecords());
    }

    @Test
    public void searchTest(){
        System.out.println(articleService.search(1, 5, "动").getRecords());
    }
}
