package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.vo.ArticleDetail;
import indi.shenqqq.bbs.model.vo.CommentVo;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.ICommentService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:25
 * @Description XX
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    IArticleService articleService;
    @Autowired
    IUserService userService;
    @Autowired
    ICommentService commentService;

    @RequestMapping("/index")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "15") Integer pageSize) {
        Page<Map<String, Object>> page = articleService.selectAll(pageNo, pageSize);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable Integer id) {
        Article article = articleService.selectById(id);
        User user = userService.selectById(article.getUserId());
        List<Comment> comments = commentService.selectByArticleId(article.getId());
        List<CommentVo> commentVos = new LinkedList<>();
        for (Comment comment : comments) {
            User comment_author = userService.selectById(comment.getUserId());
            commentVos.add(new CommentVo(comment, comment_author.getAvatar(), comment_author.getUsername()));
        }
        ArticleDetail articleDetail = new ArticleDetail(article, user, commentVos);
        return Result.success(articleDetail);
    }

    @PostMapping("/search")
    public Result searchArticle(@RequestBody Map<String, String> body) {
        Integer pageNo = body.get("pageNo") != null ? Integer.parseInt(body.get("pageNo")) : 1;
        Integer pageSize = body.get("pageSize") != null ? Integer.parseInt(body.get("pageSize")) : 15;
        String keyword = body.get("keyword");
        Page<Map<String, Object>> page = articleService.search(pageNo, pageSize, keyword);
        System.out.println(page.getTotal());
        return Result.success(page);
    }

}
