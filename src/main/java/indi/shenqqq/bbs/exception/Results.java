package indi.shenqqq.bbs.exception;

import indi.shenqqq.bbs.model.dto.Result;

/**
 * @Author Shen Qi
 * @Date 2022/4/3 10:16
 * @Description XX
 */
public class Results {
    public static Result USERNAME_EMPTY = Result.error(301,"用户名为空");
    public static Result PASSWORD_EMPTY = Result.error(302,"密码为空");
    public static Result EMAIL_EMPTY = Result.error(303,"邮箱地址为空");
    public static Result PASSWORD_FORMAT_WRONG = Result.error(304,"密码只能为a-z,A-Z,0-9组合且6-32位");
    public static Result EMAIL_FORMAT_WRONG = Result.error(305,"请输入正确的邮箱地址");

    public static Result USERNAME_ALREADY_EXISTS = Result.error(306,"用户名重复");
    public static Result EMAIL_ALREADY_EXISTS = Result.error(307,"邮箱地址重复");

    public static Result USER_DONT_EXISTS = Result.error(308,"用户不存在");
    public static Result PASSWORD_WRONG = Result.error(309,"用户名或密码错误");

    public static Result TOKEN_EMPTY = Result.error(310,"token不存在");
    public static Result STATE_INVALID = Result.error(311,"登录失效");

    public static Result TITLE_EMPTY = Result.error(312,"标题不能为空");
    public static Result ARTICLE_NOT_EXIST = Result.error(313,"文章不存在");
    public static Result NO_ACCESS = Result.error(314,"您无权访问");
    public static Result TITLE_REPEAT = Result.error(315,"标题重复");
    public static Result CONTENT_EMPTY = Result.error(316,"文章内容不能为空");
    public static Result HEADIMG_EMPTY = Result.error(317,"文章头图不能为空");
    public static Result NO_LOGOUT = Result.error(318,"请先注销");

    public static Result UPLOAD_ERROR = Result.error(319,"文件上传错误");
    public static Result TOO_MANY_FILE = Result.error(320,"上传文件过多");

    public static Result OLD_PASSWORD_WRONG = Result.error(321,"原密码错误");
    public static Result COMMENT_EMPTY = Result.error(322,"请输入评论");
    public static Result NO_RIGHT_MODIFY_ARTICLE = Result.error(323,"这不是你的文章");

    public static Result TAG_NOT_EXIST = Result.error(324,"标签不存在");
}
