package indi.shenqqq.bbs.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 17:07
 * @Description XX
 */
@Data
public class ArticleTag implements Serializable {

    private static final long serialVersionID = 1L;

    private Integer articleId;
    private Integer tagId;

}
