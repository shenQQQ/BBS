package indi.shenqqq.bbs.pointcut;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/8 16:13
 * @Description XX
 */
public class CollectServicePointcut {
    @Pointcut("execution( public * indi.shenqqq.bbs.service.IArticleService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.selectByUserIdAndArticleId(..))")
    public void selectByUserIdAndArticleId() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.selectByArticleId(..))")
    public void selectByArticleId() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.selectByUserId(..))")
    public void selectByUserId() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.save(..))")
    public void save() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.delete(..))")
    public void delete() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.deleteByArticleId(..))")
    public void deleteByArticleId() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.deleteByUserId(..))")
    public void deleteByUserId() {
    }

    @Pointcut("execution(public * indi.shenqqq.bbs.service.ICollectService.countByUserId(..))")
    public void countByUserId() {
    }
}
