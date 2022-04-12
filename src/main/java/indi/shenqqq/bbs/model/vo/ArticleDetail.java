package indi.shenqqq.bbs.model.vo;

import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.Tag;
import indi.shenqqq.bbs.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/3/10 11:09
 * @Description XX
 */

@Data
@AllArgsConstructor
public class ArticleDetail implements Serializable {
    private static final long serialVersionID = 1L;

    public ArticleDetail(Article article, User user,List<CommentVo> comments,List<Tag> tagList){
        this.id = article.getId();
        this.userId = article.getUserId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.headImg = article.getHeadImg();
        this.inTime = article.getInTime();
        this.modifyTime = article.getModifyTime();
        this.commentCount = article.getCommentCount();
        this.collectCount = article.getCollectCount();
        this.view = article.getView();
        this.top = article.getTop();
        this.good = article.getGood();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.comments = comments;
        this.list = tagList;
    }

    private Integer id;
    private String title;
    private String content;
    private String headImg;
    private Date inTime;
    private Date modifyTime;
    private Integer userId;
    private List<Tag> list;
    // 评论数
    private Integer commentCount;
    // 收藏数
    private Integer collectCount;
    // 浏览数
    private Integer view;
    // 置顶
    private Boolean top;
    // 加精
    private Boolean good;


    private String username;
    private String avatar;

    private List<CommentVo> comments;
}
