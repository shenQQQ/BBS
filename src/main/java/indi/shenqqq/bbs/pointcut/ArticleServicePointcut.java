package indi.shenqqq.bbs.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 17:05
 * @Description XX
 */
public class ArticleServicePointcut {

    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.selectByTitle(..))")
    public void selectByTitle() {
    }

    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.update(..))")
    public void update() {
    }

    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.delete(..))")
    public void delete() {
    }

    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.deleteByUserId(..))")
    public void deleteByUserId() {
    }
}
