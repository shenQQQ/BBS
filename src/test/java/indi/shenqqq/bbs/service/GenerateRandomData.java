package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.utils.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @Author Shen Qi
 * @Date 2022/3/8 10:31
 * @Description XX
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateRandomData {

    @Resource
    private IUserService userService;
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;

    @Test
    public void generateRandomUser() {
        int num = 98;
        int min = 1;
        int max = 100;
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setUsername(RandomValueUtils.getChineseName());
            user.setPassword("123456");
            user.setAvatar("https://joeschmoe.io/api/v1/" + (int) (min + Math.random() * max));
            user.setEmail(RandomValueUtils.getEmail(10, 25));
            user.setBio("不写slogan/bio的都是大懒蛋子");
            user.setInTime(new Date());
            user.setToken(TokenUtils.generateToken());
            userService.save(user);
            System.out.println(user);
        }
    }

    @Test
    public void generateRandomImg() throws IOException {
        System.out.println(RandomImgUtils.getTitleImg("哈哈哈哈哈", 640, 360, "img.png"));

    }

    @Test
    public void generateRandomArticle() {
        int num = 200;
        int min = 2;
        int max = userService.countAll();
        FileUtils fileUtils = new FileUtils();
        for (int i = 0; i < num; i++) {
            int id = (int) (min + Math.random() * max);
            User user = userService.selectById(id);
            if (user == null) {
                i--;
                continue;
            }
            String title = user.getUsername() + RandomValueUtils.getNum(0, 1000);
            Article article = articleService.selectByTitle(title);
            if (article != null) {
                i--;
                continue;
            }


            String content = RandomArticleUtils.generatorRandomArticle(title, RandomValueUtils.getNum(100, 1000));
            String filename = RandomImgUtils.getTitleImg(title, 640, 360, title);

            File file = new File(filename);

            FileInputStream input = null;
            MultipartFile multipartFile = null;

            try {
                input = new FileInputStream(file);
                multipartFile = new MockMultipartFile("file", file.getName(), "text/jpeg", input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String head_img = fileUtils.upload(multipartFile, title, "article/" + title);

            articleService.save(title, content, head_img, user);

            file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void generateRandomComments(){
        int num = 5;
        for (int i = 0; i < num; i++) {
            int articleId = RandomValueUtils.getNum(1,articleService.countAll());
            int userId = RandomValueUtils.getNum(1,userService.countAll());
            User user = userService.selectById(userId);
            Article article = articleService.selectById(articleId);
            if(article == null || user == null){
                i--;
                continue;
            }
            String content = RandomArticleUtils.generatorRandomArticle(user.getUsername(), RandomValueUtils.getNum(5,50));
            commentService.save(userId,articleId,content,article,user);
        }
    }
}
