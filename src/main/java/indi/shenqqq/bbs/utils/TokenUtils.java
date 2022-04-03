package indi.shenqqq.bbs.utils;

import indi.shenqqq.bbs.exception.ApiAssert;
import indi.shenqqq.bbs.exception.ApiExceptions;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.service.impl.UserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author Shen Qi
 * @Date 2022/3/8 10:50
 * @Description XX
 */
public class TokenUtils {

    public static String generateToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }

}
