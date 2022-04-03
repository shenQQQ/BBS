package indi.shenqqq.bbs.exception;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @Author Shen Qi
 * @Date 2022/3/14 10:29
 * @Description XX
 */
public class ApiAssert extends Assert {
    public static void isNull(Object object, ApiException e) {
        if (object != null) {
            throw e;
        }
    }


    public static void notNull(Object object, ApiException e) {
        if (object == null) {
            throw e;
        }
    }

    public static void isTrue(boolean expression, ApiException e) {
        if (!expression) {
            throw e;
        }
    }

    public static void notTrue(boolean expression, ApiException e) {
        if (expression) {
            throw e;
        }
    }

    public static void isEmpty(String txt, ApiException e) {
        if (!StringUtils.isEmpty(txt)) {
            throw e;
        }
    }

    public static void notEmpty(String txt, ApiException e) {
        if (StringUtils.isEmpty(txt)) {
            throw e;
        }
    }
}
