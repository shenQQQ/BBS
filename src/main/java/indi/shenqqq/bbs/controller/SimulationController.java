package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.model.dto.WebSocketMessage;
import indi.shenqqq.bbs.service.*;
import indi.shenqqq.bbs.socket.WebSocket;
import indi.shenqqq.bbs.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/4/6 13:13
 * @Description XX
 */
@RestController
@RequestMapping("/simulation")
@CrossOrigin
@Slf4j
public class SimulationController {
    @Resource
    private IUserService userService;
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;
    @Resource
    private ICollectService collectService;
    @Resource
    private INotificationService notificationService;

    @GetMapping("/user/{num}")
    public Result generateRandomUser(@PathVariable Integer num) {
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
        return Result.success();
    }

    @GetMapping("/article/{num}")
    public Result generateRandomArticle(@PathVariable Integer num) {
        int min = 1;
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
        return Result.success();
    }

    @GetMapping("/comment/{num}")
    public Result generateRandomComments(@PathVariable Integer num){
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
            commentService.save(new Comment(userId,articleId,content),article,user);
        }
        return Result.success();
    }

    @GetMapping("/collect/{num}")
    public Result generateRandomCollect(@PathVariable Integer num){
        for (int i = 0; i < num; i++) {
            int articleId = RandomValueUtils.getNum(1,articleService.countAll());
            int userId = RandomValueUtils.getNum(1,userService.countAll());
            User user = userService.selectById(userId);
            Article article = articleService.selectById(articleId);
            if(article == null || user == null){
                i--;
                continue;
            }
            //去重
            Collect collect = collectService.selectByUserIdAndArticleId(userId,articleId);
            if(collect != null){
                i--;
                continue;
            }
            collectService.save(articleId,user);
        }
        return Result.success();
    }

    @GetMapping("/send")
    public Result sendMessageTest(){
        WebSocket.sendMessage(1,new WebSocketMessage("hello","hello"));
        return Result.success();
    }

    @GetMapping("/new_notification")
    public Result getNewNotificationTest(){
        notificationService.save(15,1,1,"COMMENT","他说你写的不错！");
        return Result.success();
    }
}
