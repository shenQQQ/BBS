package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:30
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Date inTime;
    private Date modifyTime;
    private Integer userId;
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
}
