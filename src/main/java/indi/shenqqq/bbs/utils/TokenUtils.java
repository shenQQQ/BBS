package indi.shenqqq.bbs.utils;

import indi.shenqqq.bbs.model.User;

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
