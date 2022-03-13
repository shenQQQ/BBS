package indi.shenqqq.bbs.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import indi.shenqqq.bbs.model.Comment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/3/10 14:17
 * @Description XX
 */
@Data
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String content;
    private Date inTime;
    private Integer commentId;
    private String avatar;
    private String username;

    public CommentVo(Comment comment ,String avatar ,String username){
        this.id = comment.getId();
        this.articleId = comment.getArticleId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.inTime = comment.getInTime();
        this.commentId = comment.getCommentId();

        this.avatar = avatar;
        this.username = username;
    }
}
