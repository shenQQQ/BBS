package indi.shenqqq.bbs.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/19 18:46
 * @Description XX
 */
@RestController
@RequestMapping("/admin/comment")
@CrossOrigin
@Slf4j
public class AdminCommentController {

    @Resource
    ICommentService commentService;

    @GetMapping("/")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "0") Integer pageSize) {
        Page<Map<String, Object>> page = commentService.selectAll(pageNo, 10);
        return success(page);
    }

    @PostMapping("/search")
    public Result searchArticle(@RequestBody Map<String, String> body) {
        Integer pageNo = body.get("pageNo") != null ? Integer.parseInt(body.get("pageNo")) : 1;
        String keyword = body.get("keyword");
        Page<Map<String, Object>> page = commentService.search(pageNo, 10, keyword);
        return success(page);
    }
    @DeleteMapping("/del")
    public Result delete(@RequestBody Map<String, String> body) throws InterruptedException {
        Integer id = Integer.parseInt(body.get("id"));
        commentService.delete(commentService.selectById(id));
        return success();
    }
}
