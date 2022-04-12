package indi.shenqqq.bbs.pointcut;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Comment;
import indi.shenqqq.bbs.model.User;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 18:33
 * @Description XX
 */
public class CommentServicePointcut {

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.selectByArticleId(..))")
    public void selectByArticleId() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.deleteByArticleId(..))")
    public void deleteByArticleId() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.deleteByUserId(..))")
    public void deleteByUserId() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.save(..))")
    public void save() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.update(..))")
    public void update() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.delete(..))")
    public void delete() {
    }

    @Pointcut("execution ( public * indi.shenqqq.bbs.service.ICommentService.selectByUserId(..))")
    public void selectByUserId() {
    }
}
