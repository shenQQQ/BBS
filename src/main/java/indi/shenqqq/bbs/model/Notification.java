package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:55
 */
@Data
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private Integer articleId;
    private Integer userId;
    private Integer targetUserId;
    // 动作: REPLY, COMMENT, COLLECT
    private String action;
    private Date inTime;
    // 是否已读
    private Boolean read;
    private String content;
}
