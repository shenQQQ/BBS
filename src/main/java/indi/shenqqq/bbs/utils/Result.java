package indi.shenqqq.bbs.utils;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 11:47
 * @Description XX
 */

@Data
public class Result {
    private int code;
    private String message;
    private Object content;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object content) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("success");
        result.setContent(content);
        return result;
    }

    public static Result error() {
        return error(null);
    }

    public static Result error(String message) {
        return error(201, message);
    }

    public static Result error(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setContent(null);
        return result;
    }

    public static void error(HttpServletResponse response, int code, String message) throws IOException {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setContent(null);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JsonUtils.objectToJson(result));
    }
}
