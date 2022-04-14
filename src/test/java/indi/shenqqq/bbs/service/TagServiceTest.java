package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.service.impl.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:54
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagServiceTest {
    @Resource
    TagService tagService;

    @Test
    public void insertTagTest(){
        System.out.println(tagService.insertTag("游戏,动漫"));
    }

    @Test
    public void selectArticleByTagId(){
        System.out.println(tagService.selectTagByArticleCount(3));
    }
}
