package indi.shenqqq.bbs.model.vo;

import lombok.Data;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 13:49
 * @Description XX
 */
@Data
public class UserWithWebSocketVO implements Serializable {

    private static final long serialVersionUID = -8327007303087296114L;
    private String username;
    private Integer userId;

    public UserWithWebSocketVO() {
    }

    public UserWithWebSocketVO(String username, Integer userId) {
        this.username = username;
        this.userId = userId;
    }
}
