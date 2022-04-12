package indi.shenqqq.bbs.plugin;

import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 16:12
 * @Description XX
 */
@Component
@Aspect
@Slf4j
public class CachePlugin {

    @Resource
    private RedisService redisService;

    @Around("indi.shenqqq.bbs.pointcut.ArticleServicePointcut.selectById()")
    public Object articleSelectById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String articleJson = redisService.getString(String.format(Config.REDIS_ARTICLE_KEY, proceedingJoinPoint.getArgs()[0]));
        if (articleJson == null) {
            Object article = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            redisService.setString(String.format(Config.REDIS_ARTICLE_KEY, proceedingJoinPoint.getArgs()[0]), JsonUtils.objectToJson(article));
            return article;
        } else {
            return JsonUtils.jsonToObject(articleJson, Article.class);
        }
    }

    @After("indi.shenqqq.bbs.pointcut.ArticleServicePointcut.update()")
    public void articleUpdate(JoinPoint joinPoint) {
        Article article = (Article) joinPoint.getArgs()[0];
        redisService.setString(String.format(Config.REDIS_ARTICLE_KEY, article.getId()), JsonUtils.objectToJson(article));
    }

    @Around("indi.shenqqq.bbs.pointcut.CommentServicePointcut.selectByArticleId()")
    public Object commentSelectByArticleId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Integer articleId = (Integer) proceedingJoinPoint.getArgs()[0];
        String commentsJson = redisService.getString(String.format(Config.REDIS_COMMENTS_KEY, articleId));
        if (commentsJson != null) {
            return JsonUtils.jsonToListObject(commentsJson, Comment.class);
        } else {
            Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            redisService.setString(String.format(Config.REDIS_COMMENTS_KEY, articleId), JsonUtils.objectToJson(returnValue));
            return returnValue;
        }
    }

    @After("indi.shenqqq.bbs.pointcut.CommentServicePointcut.save()")
    public void commentInsert(JoinPoint joinPoint) {
        Comment comment = (Comment) joinPoint.getArgs()[0];
        redisService.delString(String.format(Config.REDIS_COMMENTS_KEY, comment.getArticleId()));
    }

    @After("indi.shenqqq.bbs.pointcut.CommentServicePointcut.update()")
    public void commentUpdate(JoinPoint joinPoint) {
        Comment comment = (Comment) joinPoint.getArgs()[0];
        redisService.delString(String.format(Config.REDIS_COMMENTS_KEY, comment.getArticleId()));
    }

    @After("indi.shenqqq.bbs.pointcut.CommentServicePointcut.delete()")
    public void commentDelete(JoinPoint joinPoint) {
        Comment comment = (Comment) joinPoint.getArgs()[0];
        redisService.delString(String.format(Config.REDIS_COMMENTS_KEY, comment.getArticleId()));
    }

    @Around("indi.shenqqq.bbs.pointcut.UserServicePointcut.selectByUsername()")
    public Object userSelectByUsername(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String username = (String) proceedingJoinPoint.getArgs()[0];
        String userJson = redisService.getString(String.format(Config.REDIS_USER_USERNAME_KEY, username));
        if (userJson != null) {
            return JsonUtils.jsonToObject(userJson, User.class);
        } else {
            Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            if (returnValue != null) {
                redisService.setString(String.format(Config.REDIS_USER_USERNAME_KEY, username), JsonUtils.objectToJson(returnValue));
            }
            return returnValue;
        }
    }

    @Around("indi.shenqqq.bbs.pointcut.UserServicePointcut.selectByToken()")
    public Object userSelectByToken(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String token = (String) proceedingJoinPoint.getArgs()[0];
        String userJson = redisService.getString(String.format(Config.REDIS_USER_TOKEN_KEY, token));
        if (userJson != null) {
            return JsonUtils.jsonToObject(userJson, User.class);
        } else {
            Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            if (returnValue != null) {
                redisService.setString(String.format(Config.REDIS_USER_TOKEN_KEY, token), JsonUtils.objectToJson(returnValue));
            }
            return returnValue;
        }
    }

    @Around("indi.shenqqq.bbs.pointcut.UserServicePointcut.selectById()")
    public Object userSelectById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Integer id = (Integer) proceedingJoinPoint.getArgs()[0];
        String userJson = redisService.getString(String.format(Config.REDIS_USER_ID_KEY, id));
        if (userJson != null) {
            return JsonUtils.jsonToObject(userJson, User.class);
        } else {
            Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            if (returnValue != null) {
                redisService.setString(String.format(Config.REDIS_USER_ID_KEY, id), JsonUtils.objectToJson(returnValue));
            }
            return returnValue;
        }
    }

    @After("indi.shenqqq.bbs.pointcut.UserServicePointcut.delRedisUser()")
    public void userDelRedisUser(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        redisService.delString(String.format(Config.REDIS_USER_ID_KEY, user.getId()));
        redisService.delString(String.format(Config.REDIS_USER_USERNAME_KEY, user.getUsername()));
        redisService.delString(String.format(Config.REDIS_USER_TOKEN_KEY, user.getToken()));
    }


}
