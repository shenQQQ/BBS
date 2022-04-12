package indi.shenqqq.bbs.controller;

import indi.shenqqq.bbs.exception.ApiException;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 12:49
 * @Description XX
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @GetMapping("/token")
    public Result token() {
        User user;
        try {
            user = getUserFromToken(true);
        } catch (ApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody Map<String, Object> body){
        User user1 = getUserFromToken(true);
        String username = body.get("username").toString();
        String bio = body.get("bio").toString();
        String email = body.get("email").toString();
        if (org.springframework.util.StringUtils.isEmpty(username)) return Results.USERNAME_EMPTY;
        if (org.springframework.util.StringUtils.isEmpty(email)) return Results.EMAIL_EMPTY;
        if (!StringUtils.check(email, StringUtils.EMAILREGEX)) return Results.EMAIL_FORMAT_WRONG;
        User user = userService.selectById(user1.getId());
        user.setBio(bio);
        user.setUsername(username);
        user.setEmail(email);
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/updatepwd")
    public Result updatePassword(@RequestBody Map<String, Object> body){
        User user1 = getUserFromToken(true);
        String oldPassword = body.get("old_password").toString();
        String password = body.get("password").toString();
        if (org.springframework.util.StringUtils.isEmpty(password)) return Results.PASSWORD_EMPTY;
        if (!StringUtils.check(password, StringUtils.PASSWORDREGEX)) return Results.PASSWORD_FORMAT_WRONG;
        if (!user1.getPassword().equals(oldPassword)) return Results.OLD_PASSWORD_WRONG;
        User user = userService.selectById(user1.getId());
        user.setPassword(password);
        userService.update(user);
        return Result.success();
    }

}
