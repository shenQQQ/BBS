package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 17:07
 * @Description XX
 */
@Data
@NoArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Date inTime;

    public Tag(String name){
        this.name = name;
        this.inTime = new Date();
    }
}
