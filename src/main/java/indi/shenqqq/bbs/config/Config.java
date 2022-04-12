package indi.shenqqq.bbs.config;

/**
 * @Author Shen Qi
 * @Date 2022/4/3 10:08
 * @Description XX
 */
//todo:此配置类还没做前后端配置统一，需要前后端都进行同样的修改
public class Config {
    public static String PROJECT_NAME = "SIMPLE-BBS";
    public static String PROJECT_DESCRIPTION = "a simple bbs";
    public static String AUTHOR_NAME = "Shen Qi";
    public static String AUTHOR_WEBSITE = "";
    public static String AUTHOR_EMAIL = "1017706039@qq.com";
    public static String PROJECT_VERSION = "1.0";

    public static int INDEX_PAGE_ARTICLE_NUM = 15;
    public static int INDEX_TAG_NUM = 5; //首页展示最热标签数
    public static int COLLECT_PAGE_ARTICLE_NUM = 15;
    public static int USER_PAGE_ARTICLE_NUM = 15;
    public static int SEARCH_PAGE_ARTICLE_NUM = 15;
    public static int MAX_UPLOAD_FILE_NUM = 3;
    public static int MAX_UPLOAD_VIDEO_FILE_SIZE = 3;     //单位mb
    public static int MAX_UPLOAD_IMAGE_FILE_SIZE = 3;
    public static int TAG_PAGE_SIZE = 20;

    public static String REDIS_HOST ;
    public static String REDIS_PORT ;
    public static String REDIS_PASSWORD ;
    public static String REDIS_DATABASE ;
    public static String REDIS_TIMEOUT ;

//    public static String REDIS_HOST = "119.3.8.118";
//    public static String REDIS_HOST = "shenqqq.top";
//    public static String REDIS_PORT = "6379";
//    public static String REDIS_PASSWORD = "123456";
//    public static String REDIS_DATABASE = "0";
//    public static String REDIS_TIMEOUT = "3000";

    public static String REDIS_ARTICLE_KEY = "bbs_article_%s";
    public static String REDIS_COMMENTS_KEY = "bbs_comments_%s";

    public static String REDIS_USER_ID_KEY = "bbs_user_id_%s";
    public static String REDIS_USER_USERNAME_KEY = "bbs_user_username_%s";
    public static String REDIS_USER_TOKEN_KEY = "bbs_user_token_%s";

}
