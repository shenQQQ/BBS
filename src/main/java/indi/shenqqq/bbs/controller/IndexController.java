package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.exception.ApiAssert;
import indi.shenqqq.bbs.exception.ApiException;
import indi.shenqqq.bbs.exception.ApiExceptions;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static indi.shenqqq.bbs.utils.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:34
 * @Description XX
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class IndexController extends BaseController {

    @Autowired
    IArticleService articleService;
    @Autowired
    private IUserService userService;


    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping({"/", "/index"})
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "0") Integer pageSize) {
        pageSize = pageSize == 0 ? Config.INDEX_PAGE_ARTICLE_NUM : pageSize;
        Page<Map<String, Object>> page = articleService.selectAll(pageNo, pageSize);
        return success(page);
    }

    @PostMapping("/signup")
    public Result signup(@RequestBody Map<String, Object> body, HttpSession session) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String username = body.get("username").toString();
        String password = body.get("password").toString();
        String email = body.get("email").toString();
        //todo:利用session实现一次性验证码
        if (org.springframework.util.StringUtils.isEmpty(username)) return Results.USERNAME_EMPTY;
        if (org.springframework.util.StringUtils.isEmpty(password)) return Results.PASSWORD_EMPTY;
        if (org.springframework.util.StringUtils.isEmpty(email)) return Results.EMAIL_EMPTY;
        if (StringUtils.check(username, StringUtils.USERNAMEREGEX)) return Results.PASSWORD_FORMAT_WRONG;
        if (StringUtils.check(email, StringUtils.EMAILREGEX)) return Results.EMAIL_FORMAT_WRONG;
        if (!org.springframework.util.StringUtils.isEmpty(request.getHeader("Authorization"))) return Results.NO_LOGOUT;
        if (userService.selectByUsername(username) == null) return Results.USERNAME_ALREADY_EXISTS;
        if (userService.selectByEmail(email) == null) return Results.EMAIL_ALREADY_EXISTS;
        //todo:生成默认头像
        User user = userService.addUser(username, password, null, email, null);
        return doUserStorage(session, user);
    }


    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> body, HttpSession session) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String username = body.get("username").toString();
        String password = body.get("password").toString();
        User user = userService.selectByUsername(username);
        if (org.springframework.util.StringUtils.isEmpty(username)) return Results.USERNAME_EMPTY;
        if (org.springframework.util.StringUtils.isEmpty(password)) return Results.PASSWORD_EMPTY;
        if (!org.springframework.util.StringUtils.isEmpty(request.getHeader("Authorization"))) return Results.NO_LOGOUT;
        if (user == null) return Results.USER_DONT_EXISTS;
        if (password.equals(user.getPassword())) return Results.PASSWORD_WRONG;
        return this.doUserStorage(session, user);
    }

    @PostMapping("/token")
    public Result loginToken(@RequestBody String token, HttpSession session) {
        token = token.substring(0, token.length() - 1);
        User user = userService.selectByToken(token);
        if(org.springframework.util.StringUtils.isEmpty(token)) Result.error();
        if(user == null) return Results.STATE_INVALID;
        return this.doUserStorage(session, user);
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile[] files, String type, HttpSession session) {
        FileUtils fileUtil = new FileUtils();
        User user = getUserFromToken(true);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String url;
            MultipartFile file = files[i];
            String suffix = "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            if (!Arrays.asList(".jpg", ".png", ".gif", ".jpeg", ".mp4").contains(suffix.toLowerCase())) {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "文件格式不正确");
                continue;
            }
            long size = file.getSize();
            // 根据不同上传类型，对文件大小做校验
            if (type.equalsIgnoreCase("video")) {
                long uploadVideoSizeLimit = 5;
                if (size > uploadVideoSizeLimit * 1024 * 1024) {
                    errors.add("第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadVideoSizeLimit + "MB 以内");
                    continue;
                }
            } else {
                long uploadImageSizeLimit = 5;
                if (size > uploadImageSizeLimit * 1024 * 1024) {
                    errors.add("第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadImageSizeLimit + "MB 以内");
                    continue;
                }
            }
            if (type.equalsIgnoreCase("avatar")) { // 上传头像
                // 拿到上传后访问的url
                url = fileUtil.upload(file, "avatar", "avatar/" + user.getUsername());
                if (url != null) {
                    // 查询当前用户的最新信息
                    User user1 = userService.selectById(user.getId());
                    user1.setAvatar(url);
                    // 保存用户新的头像
                    userService.update(user1);
                    // 将最新的用户信息更新在session里
                    if (session != null) session.setAttribute("_user", user1);
                }
            } else if (type.equalsIgnoreCase("article")) { // 发帖上传图片
                url = fileUtil.upload(file, null, "article/" + user.getUsername());
            } else if (type.equalsIgnoreCase("headImg")) { // 发帖上传图片
                url = fileUtil.upload(file, null, "article/headImg/" + user.getUsername());
            } else if (type.equalsIgnoreCase("video")) { // 视频上传
                url = fileUtil.upload(file, null, "video/" + user.getUsername());
            } else {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "上传图片类型不在处理范围内");
                continue;
            }
            if (url == null) {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "上传的文件不存在或者上传过程发生了错误");
                continue;
            }
            urls.add(url);
        }
        if(errors.size() != 0){
            resultMap.put("urls", urls);
            resultMap.put("errors", errors);
            return Results.UPLOAD_ERROR;
        }
        resultMap.put("urls", urls);
        return success(resultMap);
    }

}
