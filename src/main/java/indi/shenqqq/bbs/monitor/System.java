package indi.shenqqq.bbs.monitor;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Shen Qi
 * @Date 2022/4/20 14:08
 * @Description XX
 */
@Data
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
