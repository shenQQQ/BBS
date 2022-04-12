package indi.shenqqq.bbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.exception.Results;
import indi.shenqqq.bbs.model.Tag;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.model.dto.Result;
import indi.shenqqq.bbs.service.ITagService;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static indi.shenqqq.bbs.model.dto.Result.success;

/**
 * @Author Shen Qi
 * @Date 2022/3/4 18:34
 * @Description XX
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
@Slf4j
public class IndexController extends BaseController {

    @Autowired
    private IUserService userService;
    @Resource
    private ITagService tagService;

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
        if (!StringUtils.check(password, StringUtils.PASSWORDREGEX)) return Results.PASSWORD_FORMAT_WRONG;
        if (!StringUtils.check(email, StringUtils.EMAILREGEX)) return Results.EMAIL_FORMAT_WRONG;
        if (!org.springframework.util.StringUtils.isEmpty(request.getHeader("Authorization"))) return Results.NO_LOGOUT;
        if (userService.selectByUsername(username) != null) return Results.USERNAME_ALREADY_EXISTS;
        if (userService.selectByEmail(email) != null) return Results.EMAIL_ALREADY_EXISTS;
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
        if (!password.equals(user.getPassword())) return Results.PASSWORD_WRONG;
        return this.doUserStorage(session, user);
    }

    @PostMapping("/token")
    public Result loginToken(@RequestBody String token, HttpSession session) {
        token = token.substring(0, token.length() - 1);
        User user = userService.selectByToken(token);
        if (org.springframework.util.StringUtils.isEmpty(token)) Result.error();
        if (user == null) return Results.STATE_INVALID;
        return this.doUserStorage(session, user);
    }

    @GetMapping("/tag/{id}")
    public Result articleByTagName(@RequestParam(defaultValue = "1") Integer pageNo, @PathVariable int id) {
        Tag tag = tagService.selectById(id);
        if (tag == null) {
            return Results.TAG_NOT_EXIST;
        } else {
            Page<Map<String, Object>> iPage = tagService.selectArticleByTagId(tag.getId(), pageNo);
            Map<String, Object> result = new HashMap<>();
            result.put("tag", tag);
            result.put("page", iPage);
            return success(result);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile[] files, String type, HttpSession session) {
        FileUtils fileUtil = new FileUtils();
        User user = getUserFromToken(true);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        long uploadVideoSizeLimit = Config.MAX_UPLOAD_VIDEO_FILE_SIZE;
        long uploadImageSizeLimit = Config.MAX_UPLOAD_IMAGE_FILE_SIZE;
        if (files.length > Config.MAX_UPLOAD_FILE_NUM) return Results.TOO_MANY_FILE;
        for (int i = 0; i < files.length; i++) {
            String url;
            MultipartFile file = files[i];
            String suffix = "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            if (!Arrays.asList(".jpg", ".png", ".gif", ".jpeg", ".mp4").contains(suffix.toLowerCase())) {
                return Result.error(319, "第[" + (i + 1) + "]个文件异常: " + "文件格式不正确,请确保上传文件是jpg,png,gif,jpeg,mp4格式");
            }
            long size = file.getSize();
            if (type.equalsIgnoreCase("video")) {
                if (size > uploadVideoSizeLimit * 1024 * 1024) {
                    return Result.error(319, "第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadVideoSizeLimit + "MB 以内");
                }
            } else {
                if (size > uploadImageSizeLimit * 1024 * 1024) {
                    return Result.error(319, "第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadImageSizeLimit + "MB 以内");
                }
            }
            if (type.equalsIgnoreCase("avatar")) { // 上传头像
                // 拿到上传后访问的url
                url = fileUtil.upload(file, "avatar", "avatar/" + user.getUsername());
                if (url != null) {
                    User user1 = userService.selectById(user.getId());
                    user1.setAvatar(url);
                    userService.update(user1);
                    if (session != null) session.setAttribute("_user", user1);
                }
            } else if (type.equalsIgnoreCase("article")) { // 发帖上传图片
                url = fileUtil.upload(file, null, "article/" + user.getUsername());
            } else if (type.equalsIgnoreCase("headImg")) { // 发帖上传图片
                url = fileUtil.upload(file, null, "article/" + user.getUsername() + "/headImg/");
            } else if (type.equalsIgnoreCase("video")) { // 视频上传
                url = fileUtil.upload(file, null, "video/" + user.getUsername());
            } else {
                return Result.error(319, "第[" + (i + 1) + "]个文件异常: " + "上传文件类型不在处理范围内");
            }
            if (url == null) {
                return Result.error(319, "第[" + (i + 1) + "]个文件异常: " + "上传的文件不存在或者上传过程发生了错误");
            }
            urls.add(url);
        }
        resultMap.put("urls", urls);
        return success(resultMap);
    }

    @GetMapping("/config")
    public Result config() {
        List<Tag> tagList = tagService.selectTagByArticleCount(Config.INDEX_TAG_NUM);
        List<Map<String,String>> list = new LinkedList<>();
        for (Tag tag : tagList) {
            Map<String,String> map = new HashMap<>();
            map.put("key","/tag/" + tag.getId());
            map.put("title" ,tag.getName());
            list.add(map);
        }
        System.out.println(JsonUtils.objectToJson(list));
        return success(list);
    }
}
