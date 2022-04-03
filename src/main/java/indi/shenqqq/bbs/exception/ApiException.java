package indi.shenqqq.bbs.exception;

import lombok.Data;

/**
 * @Author Shen Qi
 * @Date 2022/3/14 10:30
 * @Description XX
 */
@Data
public class ApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
