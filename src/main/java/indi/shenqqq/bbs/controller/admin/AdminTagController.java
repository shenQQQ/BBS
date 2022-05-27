package indi.shenqqq.bbs.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/4/19 18:47
 * @Description XX
 */
@RestController
@RequestMapping("/admin/tag")
@CrossOrigin
@Slf4j
public class AdminTagController {

    @Resource
    ITagService tagService;

    @GetMapping("/")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo) {
        Page<Map<String, Object>> page = tagService.selectAllTag(pageNo);
        return success(page);
    }

    @DeleteMapping("/del")
    public Result delete(@RequestBody Map<String, String> body) throws InterruptedException {
        Integer id = Integer.parseInt(body.get("id"));
        tagService.delete(id);
        return success();
    }

    @PostMapping("/search")
    public Result search(@RequestBody Map<String, String> body) {
        Integer pageNo = body.get("pageNo") != null ? Integer.parseInt(body.get("pageNo")) : 1;
        String keyword = body.get("keyword");
        Page<Map<String, Object>> page = tagService.search(pageNo, keyword);
        return success(page);
    }

}
