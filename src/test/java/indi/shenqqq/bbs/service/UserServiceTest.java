package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 16:59
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @Resource
    private IUserService userService;

    private String USERNAME = "shenqi";
    private static User example =  new User();
    static {
        example.setId(1);
        example.setUsername("shenqi");
        example.setPassword("123456");
        example.setAvatar("http://shenqqq.top:8080/static/upload/avatar/root/avatar.jpeg?v=452096");
        example.setEmail("1017706039@qq.com");
        example.setBio("一个兴趣使然的英雄");
        example.setToken("ef431efc-dd8c-4909-a8d3-2034e61a8e5c");
    }

    @Test
    public void selectByUsernameTest(){
        User user = userService.selectByUsername(example.getUsername());
        assert(example.getUsername().equals(user.getUsername()));
    }

    @Test
    public void selectByTokenTest(){
        User user = userService.selectByToken(example.getToken());
        assert(example.getToken().equals(user.getToken()));
    }

    @Test
    public void selectByEmailTest(){
        User user = userService.selectByEmail(example.getEmail());
        assert(example.getEmail().equals(user.getEmail()));
    }

    @Test
    public void selectByIdTest(){
        User user = userService.selectById(example.getId());
        assert(example.getId().equals(user.getId()));
    }

    @Test
    public void addAndDeleteUserTest(){
        User user = new User();
        user.setUsername("shenqqq");
        user.setPassword("1234567");
        user.setAvatar("http://shenqqq.top:8081/static/upload/avatar/root/avatar.jpeg?v=452096");
        user.setEmail("1017706038@qq.com");
        user.setBio("两个兴趣使然的英雄");
        User result = userService.addUser(user.getUsername(),user.getPassword(),user.getAvatar(),user.getEmail(),user.getBio());
        assert(user.getUsername().equals(result.getUsername()));
        userService.deleteUser(result.getId());
        Assert.assertNull(userService.selectByToken(user.getToken()));
    }
}
