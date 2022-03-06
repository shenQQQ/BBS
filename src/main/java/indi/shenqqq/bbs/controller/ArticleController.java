package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:25
 * @Description XX
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    IArticleService articleService;

    @RequestMapping("/index")
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Map<String, Object>> page = articleService.selectAll(pageNo,pageSize);
        return Result.success(page);
    }

}
