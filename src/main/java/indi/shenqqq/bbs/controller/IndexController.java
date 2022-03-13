package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:34
 * @Description XX
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class IndexController {
    @Autowired
    IArticleService articleService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping({"/", "/index"})
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "15") Integer pageSize) {
        Page<Map<String, Object>> page = articleService.selectAll(pageNo,pageSize);
        return Result.success(page);
    }
}
