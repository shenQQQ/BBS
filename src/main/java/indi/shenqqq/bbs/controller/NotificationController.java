package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.service.INotificationService;
import indi.shenqqq.bbs.socket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Options;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/13 17:06
 * @Description XX
 */
@RestController
@RequestMapping("/notification")
@CrossOrigin
@Slf4j
public class NotificationController extends BaseController {
    @Resource
    private INotificationService notificationService;

    @GetMapping("/notRead")
    public Result notRead() {
        User user = getUserFromToken(true);
        return success(notificationService.countNotRead(user.getId()));
    }

    @GetMapping("/mark")
    public Result markRead() {
        User user = getUserFromToken(true);
        notificationService.markRead(user.getId());
        return success();
    }

    // 通知列表
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = "0") Integer pageSize) {
        User user = getUserFromToken(true);
        pageSize = pageSize == 0 ? Config.NOTIFICATION_PAGE_SIZE : pageSize;
        if (userService.selectById(user.getId()) == null) return Results.USER_DONT_EXISTS;
        if (!userAuth()) return Results.NO_ACCESS;
        return success(notificationService.selectAll(pageNo,pageSize,user.getId()));
    }
}
