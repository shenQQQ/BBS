package indi.shenqqq.bbs.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/19 15:15
 * @Description XX
 */
@RestController
@RequestMapping("/admin/user")
@CrossOrigin
@Slf4j
public class AdminUserController {

    @Resource
    IUserService userService;

    @GetMapping("/")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "0") Integer pageSize) {
        pageSize = pageSize == 0 ? Config.BACKSTAGE_USER_SIZE : pageSize;
        Page<Map<String, Object>> page = userService.selectAll(pageNo, pageSize);
        return success(page);
    }

    @PostMapping("/search")
    public Result searchArticle(@RequestBody Map<String, String> body) {
        Integer pageNo = body.get("pageNo") != null ? Integer.parseInt(body.get("pageNo")) : 1;
        Integer pageSize = body.get("pageSize") != null ? Integer.parseInt(body.get("pageSize")) : Config.BACKSTAGE_USER_SIZE;
        String keyword = body.get("keyword");
        Page<Map<String, Object>> page = userService.search(pageNo, pageSize, keyword);
        return success(page);
    }

    @GetMapping("/count")
    public Result countAll() {
        return success(userService.countAll());
    }

    @DeleteMapping("/del")
    public Result delete(@RequestBody Map<String, String> body) throws InterruptedException {
        Integer id = Integer.parseInt(body.get("id"));
        userService.deleteUser(id);
        return success();
    }
}
