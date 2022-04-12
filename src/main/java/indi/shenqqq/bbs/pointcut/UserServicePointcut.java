package indi.shenqqq.bbs.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 18:24
 * @Description XX
 */
public class UserServicePointcut {
    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.selectByUsername(..))")
    public void selectByUsername() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.selectByToken(..))")
    public void selectByToken() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.selectByEmail(..))")
    public void selectByEmail() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.addUser(..))")
    public void addUser() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.save(..))")
    public void save() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.deleteUser(..))")
    public void deleteUser() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.update(..))")
    public void update() {
    }

    @Pointcut("execution (public * indi.shenqqq.bbs.service.IUserService.delRedisUser(..))")
    public void delRedisUser() {
    }
}
