package indi.shenqqq.bbs.model.vo;

import cn.hutool.core.net.NetUtil;
import com.sun.management.OperatingSystemMXBean;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.util.Properties;

/**
 * @Author Shen Qi
 * @Date 2022/4/20 15:52
 * @Description XX
 */
@Data
public class SimpleMonitorVo {
    public long totalMemorySize;
    public long usedMemory;
    public String OsName;
    public String UserDir;
    public String onLineUser;
}
