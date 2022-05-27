package indi.shenqqq.bbs.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author Shen Qi
 * @Date 2022/5/16 13:24
 * @Description XX
 */
public class IndexServicePointcut {

    @Pointcut("execution(public * indi.shenqqq.bbs.service.IIndexService.indexAllArticle(..))")
    public void indexAllArticle() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.IIndexService.indexArticle(..))")
    public void indexArticle() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.IIndexService.deleteArticleIndex(..))")
    public void deleteArticleIndex() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.IIndexService.batchDeleteIndex(..))")
    public void batchDeleteIndex() {
    }

}
