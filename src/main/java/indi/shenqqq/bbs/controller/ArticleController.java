package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.vo.ArticleDetail;
import indi.shenqqq.bbs.model.vo.CommentVo;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.ICollectService;
import indi.shenqqq.bbs.service.ICommentService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static indi.shenqqq.bbs.utils.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:25
 * @Description XX
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController extends BaseController {

    @Autowired
    IArticleService articleService;
    @Autowired
    IUserService userService;
    @Autowired
    ICommentService commentService;
    @Autowired
    ICollectService collectService;

    @RequestMapping("/index")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "0") Integer pageSize) {
        pageSize = pageSize == 0 ? Config.INDEX_PAGE_ARTICLE_NUM : pageSize;
        Page<Map<String, Object>> page = articleService.selectAll(pageNo, pageSize);
        return success(page);
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable Integer id) {
        Article article = articleService.selectById(id);
        if (article == null) return Results.ARTICLE_NOT_EXIST;
        User user = userService.selectById(article.getUserId());
        //todo:用户删除后该不该保留帖子
        List<Comment> comments = commentService.selectByArticleId(article.getId());
        List<CommentVo> commentVos = new LinkedList<>();
        for (Comment comment : comments) {
            User comment_author = userService.selectById(comment.getUserId());
            commentVos.add(new CommentVo(comment, comment_author.getAvatar(), comment_author.getUsername()));
        }
        ArticleDetail articleDetail = new ArticleDetail(article, user, commentVos);
        return success(articleDetail);
    }

    @GetMapping("/collect/{userId}")
    public Result getCollectArticleByUserId(@PathVariable Integer userId,
                                            @RequestParam(defaultValue = "1") Integer pageNo,
                                            @RequestParam(defaultValue = "0") Integer pageSize) {

        pageSize = pageSize == 0 ? Config.COLLECT_PAGE_ARTICLE_NUM : pageSize;
        if (userService.selectById(userId) == null) return Results.USER_DONT_EXISTS;
        if (!userAuth(userId)) return Results.NO_ACCESS;
        return success(collectService.selectByUserId(userId, pageNo, pageSize));
    }

    @GetMapping("/user/{userId}")
    public Result getArticleByUserId(@PathVariable Integer userId,
                                     @RequestParam(defaultValue = "1") Integer pageNo,
                                     @RequestParam(defaultValue = "0") Integer pageSize) {
        pageSize = pageSize == 0 ? Config.USER_PAGE_ARTICLE_NUM : pageSize;
        if (userService.selectById(userId) == null) return Results.USER_DONT_EXISTS;
        if (!userAuth()) return Results.NO_ACCESS;
        return success(articleService.selectByUserId(userId, pageNo, pageSize));
    }

    @PostMapping("/search")
    public Result searchArticle(@RequestBody Map<String, String> body) {
        Integer pageNo = body.get("pageNo") != null ? Integer.parseInt(body.get("pageNo")) : 1;
        Integer pageSize = body.get("pageSize") != null ? Integer.parseInt(body.get("pageSize")) : Config.SEARCH_PAGE_ARTICLE_NUM;
        String keyword = body.get("keyword");
        Page<Map<String, Object>> page = articleService.search(pageNo, pageSize, keyword);
        return success(page);
    }

    @PostMapping("/save")
    public Result create(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        String content = body.get("content");
        String headImg = body.get("headImg");
        if(StringUtils.isEmpty(title)) return Results.TITLE_EMPTY;
        if(StringUtils.isEmpty(content)) return Results.CONTENT_EMPTY;
        if(StringUtils.isEmpty(headImg)) return Results.HEADIMG_EMPTY;
        if(articleService.selectByTitle(title) != null) return Results.TITLE_REPEAT;
        articleService.save(title, content, headImg, getUserFromToken(true));
        return success();
    }

}
