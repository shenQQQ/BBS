package indi.shenqqq.bbs.exception;

/**
 * @Author Shen Qi
 * @Date 2022/3/14 10:32
 * @Description XX
 */
public class ApiExceptions {

    public static ApiException USERNAME_EMPTY = new ApiException(301,"用户名为空");
    public static ApiException PASSWORD_EMPTY = new ApiException(302,"密码为空");
    public static ApiException EMAIL_EMPTY = new ApiException(303,"邮箱地址为空");
    public static ApiException PASSWORD_FORMAT_WRONG = new ApiException(304,"用户名只能为a-z,A-Z,0-9组合且2-16位");
    public static ApiException EMAIL_FORMAT_WRONG = new ApiException(305,"请输入正确的邮箱地址");

    public static ApiException USERNAME_ALREADY_EXISTS = new ApiException(306,"用户名重复");
    public static ApiException EMAIL_ALREADY_EXISTS = new ApiException(307,"邮箱地址重复");

    public static ApiException USER_DONT_EXISTS = new ApiException(308,"用户不存在");
    public static ApiException PASSWORD_WRONG = new ApiException(309,"用户名或密码错误");

    public static ApiException TOKEN_EMPTY = new ApiException(310,"token不存在");
    public static ApiException STATE_INVALID = new ApiException(311,"登录失效");

    public static ApiException TITLE_EMPTY = new ApiException(312,"标题不能为空");
}
