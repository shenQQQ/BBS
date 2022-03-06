package indi.shenqqq.bbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:14
 */
@Data
public class User implements Serializable {

    private static final long serialVersionID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String avatar;
    private String email;
    private String bio;
    private Integer score;
    private Date inTime;
    private String token;

}
