package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.exception.ApiException;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.ICollectService;
import indi.shenqqq.bbs.model.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Shen Qi
 * @Date 2022/4/5 13:18
 * @Description XX
 */
@RestController
@RequestMapping("/collect")
@CrossOrigin
@Slf4j
public class CollectController extends BaseController {

    @Autowired
    IArticleService articleService;
    @Autowired
    ICollectService collectService;

    @GetMapping("/{articleId}")
    public Result isCollectByArticleIdAndUserId(@PathVariable Integer articleId) {
        User user;
        try {
            user = getUserFromToken(true);
        } catch (ApiException e) {
            return Result.error(e.getCode(),e.getMessage());
        }
        Article article = articleService.selectById(articleId);
        if (article == null) return Results.ARTICLE_NOT_EXIST;
        Collect collect = collectService.selectByUserIdAndArticleId(user.getId(), articleId);
        return collect == null ? Result.success(false) : Result.success(true);
    }

    @PostMapping("/{articleId}")
    public Result addCollect(@PathVariable Integer articleId){
        User user = getUserFromToken(true);
        Article article = articleService.selectById(articleId);
        if (article == null) return Results.ARTICLE_NOT_EXIST;
        collectService.save(articleId,user);
        return Result.success();
    }

    @DeleteMapping("/{articleId}")
    public Result deleteCollect(@PathVariable Integer articleId) {
        User user = getUserFromToken(true);
        Article article = articleService.selectById(articleId);
        if (article == null) return Results.ARTICLE_NOT_EXIST;
        collectService.delete(articleId,user.getId());
        return Result.success();
    }
}
