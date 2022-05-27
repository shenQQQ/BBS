package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/21 15:15
 * @Description XX
 */
@Data
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("`key`")
    private String key;

    private String value;

    private String description;
}
