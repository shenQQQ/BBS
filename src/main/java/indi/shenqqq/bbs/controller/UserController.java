package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.service.impl.UserService;
import indi.shenqqq.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 12:49
 * @Description XX
 */
@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IArticleService articleService;
//
//    @GetMapping("/{username}")
//    public Result index(@PathVariable String username) {
//        User user = userService.selectByUsername(username);
//        return Result.success(user);
//    }
//
//    @GetMapping("/{username}/articles")
//    public Result articles(@PathVariable String username,
//                           @RequestParam(defaultValue = "1") Integer pageNo,
//                           @RequestParam(defaultValue = "5")Integer pageSize){
//        User user = userService.selectByUsername(username);
//        if(user == null){
//            return Result.error("用户不存在");
//        }
//        Page<Map<String,Object>> page = articleService.selectByUserId(user.getId(), pageNo, pageSize);
//        return Result.success(page);
//    }


}
