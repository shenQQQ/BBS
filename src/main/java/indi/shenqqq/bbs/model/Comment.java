package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String content;
    private Date inTime;
    private Integer commentId;

    public Comment(int userId, int articleId, String content) {
        this.setArticleId(articleId);
        this.setUserId(userId);
        this.setContent(content);
        this.setInTime(new Date());
    }
}
