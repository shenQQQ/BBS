package indi.shenqqq.bbs.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import indi.shenqqq.bbs.model.Recommend;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/5/17 13:40
 * @Description XX
 */
@Data
public class RecommendVo implements Serializable {
    private static final long serialVersionID = 1L;

    private Integer id;
    private Integer articleId;
    private String head_img;

    public RecommendVo(Recommend re, String head_img) {
        this.id = re.getId();
        this.articleId = re.getArticleId();
        this.head_img = head_img;
    }
}
