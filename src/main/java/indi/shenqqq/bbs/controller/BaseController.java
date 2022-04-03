package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.exception.ApiAssert;
import indi.shenqqq.bbs.exception.ApiExceptions;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.CookieUtils;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static indi.shenqqq.bbs.utils.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/3 10:40
 * @Description XX
 */
public class BaseController {

    @Autowired
    IUserService userService;
    @Resource
    private CookieUtils cookieUtils;

    /**
     * @param:  required（是否强制要求包含token）
     */
    public User getUserFromToken(boolean required) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (required) {
            ApiAssert.notEmpty(token, ApiExceptions.TOKEN_EMPTY);
            User user = userService.selectByToken(token);
            ApiAssert.notNull(user, ApiExceptions.STATE_INVALID);
            return user;
        } else {
            if (org.springframework.util.StringUtils.isEmpty(token)) return null;
            return userService.selectByToken(token);
        }
    }

    public boolean userAuth(){
        if(getUserFromToken(true).getId() == null) return false;
        return true;
    }

    public boolean userAuth(int userId){
        if(getUserFromToken(true).getId() != userId) return false;
        return true;
    }

    /**
     * 将注册与登录后的用户存进session和cookie中
     */
    public Result doUserStorage(HttpSession session, User user) {
        // 将用户信息写session
        if (session != null) {
            session.setAttribute("_user", user);
            session.removeAttribute("_captcha");
        }
        // 将用户token写cookie
        cookieUtils.setCookie("user_token", user.getToken());
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", user.getToken());
        return success(map);
    }
}
