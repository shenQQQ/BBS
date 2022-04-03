package indi.shenqqq.bbs.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author Shen Qi
 * @Date 2022/3/14 11:46
 * @Description XX
 */
@Component
public class CookieUtils {

    //cookie存储时间，单位是秒
    private static Integer COOKIE_MAX_AGE = 604801;
    //存cookie时的域名，要与网站发布地址一致
    private static String COOKIE_DOMAIN = "localhost";

    public void setCookie(String key, String value) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.parseInt(COOKIE_MAX_AGE.toString()));
        cookie.setDomain(COOKIE_DOMAIN);
        assert response != null;
        response.addCookie(cookie);
    }


    public String getCookie(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    // 清除cookie
    public void clearCookie(String name) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        assert response != null;
        response.addCookie(cookie);
    }
}
