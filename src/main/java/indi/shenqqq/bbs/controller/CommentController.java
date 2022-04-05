package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.exception.ApiException;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.ICommentService;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/10 14:03
 * @Description XX
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController extends BaseController{

    @Autowired
    ICommentService commentService;
    @Autowired
    IArticleService articleService;

    @PostMapping("/{articleId}")
    public Result getArticleByUserId(@PathVariable Integer articleId,
                                     @RequestBody Map<String, String> body) {
        User user = getUserFromToken(true);
        Article article = articleService.selectById(articleId);
        String comment = body.get("comment");
        if (article == null) return Results.ARTICLE_NOT_EXIST;
        if (StringUtils.isEmpty(comment)) return Results.COMMENT_EMPTY;
        commentService.save(user.getId(),articleId,comment,article,user);
        return Result.success();
    }
}
