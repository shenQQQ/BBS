package indi.shenqqq.bbs.controller.admin;

import com.sun.management.OperatingSystemMXBean;
import indi.shenqqq.bbs.model.Ad;
import indi.shenqqq.bbs.model.Recommend;
import indi.shenqqq.bbs.model.Tag;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.model.vo.SimpleMonitorVo;
import indi.shenqqq.bbs.monitor.Server;
import indi.shenqqq.bbs.service.IAdService;
import indi.shenqqq.bbs.service.INotificationService;
import indi.shenqqq.bbs.service.IRecommendService;
import indi.shenqqq.bbs.service.ISystemConfigService;
import indi.shenqqq.bbs.socket.WebSocket;
import indi.shenqqq.bbs.utils.SystemMonitorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.util.*;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/14 19:09
 * @Description XX
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
@Slf4j
public class AdminIndexController {

    @Resource
    ISystemConfigService systemConfigService;
    @Resource
    IAdService adService;
    @Resource
    IRecommendService recommendService;
    @Resource
    INotificationService notificationService;

    @GetMapping("/dashboard")
    public Result index() {
//      new Server().copyTo();
//      SystemMonitorUtils.getSysMonitor();
        int kb = 1024;
        SimpleMonitorVo vo = new SimpleMonitorVo();
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        vo.totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        vo.usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        vo.OsName = System.getProperties().getProperty("os.name");
        vo.UserDir = System.getProperties().getProperty("user.dir");
        vo.onLineUser = null;
        if (true) {
            vo.onLineUser = WebSocket.online.toString();
        }
        return success(vo);
    }

    @PostMapping("/broadcast")
    public Result broadcast(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        StringBuffer result = new StringBuffer();
        if (!StringUtils.isEmpty(systemConfigService.selectByKey("websocket_address"))) {
            WebSocket.onlineBroadcast(message);
        }else {
            result.append("未配置ws，不能实时广播在线用户。");
        }
        if (!StringUtils.isEmpty(systemConfigService.selectByKey("kafka_ip"))) {
            notificationService.broadcast(message);
        }else {
            result.append("未配置kafka，不能通知全站。");
        }
        return StringUtils.isEmpty(result.toString()) ? success() : success(result.toString());
    }

    @GetMapping("/recommend")
    public Result recommend() {
        return success(recommendService.selectStr());
    }

    //https://image.gcores.com/3aa067dc-e46a-45d9-be6b-f48785632155.jpg?x-oss-process=image/resize,limit_1,m_fill,w_328,h_395/quality,q_90
    //https://image.gcores.com/881075d6-327b-4b4b-b95d-dce8595df8d1.jpg?x-oss-process=image/resize,limit_1,m_fill,w_328,h_395/quality,q_90
    @GetMapping("/ad")
    public Result ad() {
        return success(adService.selectAd());
    }

    @GetMapping("/config")
    public Result config() {
        return success(systemConfigService.selectAllConfig());
    }

    @PostMapping("/updateconfig")
    public Result update(@RequestBody Map<String, String> body) {
        systemConfigService.update(body);
        return success();
    }

    @PostMapping("/updatead")
    public Result updatead(@RequestBody Map<String, String> body) {
        adService.updateAd(new Ad(1, body.get("1")));
        return success();
    }

    @PostMapping("/updaterecommend")
    public Result updaterecommend(@RequestBody Map<String, String> body) {
        String s = body.get("1");
        String[] _ids = StringUtils.commaDelimitedListToStringArray(s);
        Set<Integer> set = new LinkedHashSet();
        for (String _id : _ids) {
            _id = _id.trim();
            if (StringUtils.isEmpty(_id)) {
                continue;
            }
            set.add(Integer.parseInt(_id));
        }
        List<Recommend> result = new ArrayList<>();
        for (Integer integer : set) {
            result.add(new Recommend(integer));
        }
        recommendService.update(result);
        return success();
    }

}
