package indi.shenqqq.bbs.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 11:05
 * @Description XX
 */
@Data
public class WebSocketMessage implements Serializable {

    private static final long serialVersionUID = 1244988167184789309L;

    private String type;
    private Object payload;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

}

