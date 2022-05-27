package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/22 18:32
 * @Description XX
 */
@Data
public class Ad implements Serializable {
    private static final long serialVersionID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;

    public Ad(int i, String body) {
        this.id = i;
        this.url = body;
    }
}
