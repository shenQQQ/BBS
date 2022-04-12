package indi.shenqqq.bbs.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author Shen Qi
 * @Date 2022/4/8 16:17
 * @Description XX
 */
public class FileUtilPointcut {
    @Pointcut("execution( public * indi.shenqqq.bbs.utils.FileUtils.upload(..))")
    public void upload() {
    }
}
