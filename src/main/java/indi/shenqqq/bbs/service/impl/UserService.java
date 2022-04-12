package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.shenqqq.bbs.dao.UserMapper;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.IUserService;
import indi.shenqqq.bbs.utils.SpringContextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 16:54
 */
//todo 注册新用户密码未加密
@Service
@Transactional
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    private String generateToken() {
        String token = UUID.randomUUID().toString();
        User user = this.selectByToken(token);
        if (user != null) {
            return this.generateToken();
        }
        return token;
    }

    @Override
    public User selectByToken(String token) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getToken, token);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getEmail, email);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectById(Integer id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getId, id);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> selectTopList(Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("score").last("limit " + limit);
        return userMapper.selectList(wrapper);
    }

    @Override
    public User addUser(String username,
                        String password,
                        String avator,
                        String email,
                        String bio) {
        String token = this.generateToken();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAvatar(avator);
        user.setEmail(email);
        user.setBio(bio);
        user.setInTime(new Date());
        user.setToken(token);
        userMapper.insert(user);
        return this.selectById(user.getId());
    }

    @Override
    public void save(User user) {
        SpringContextUtils.getBean(UserService.class).delRedisUser(user);
        userMapper.insert(user);
    }


    @Override
    public void deleteUser(Integer id) {
        User user = this.selectById(id);
        SpringContextUtils.getBean(UserService.class).delRedisUser(user);
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        SpringContextUtils.getBean(UserService.class).delRedisUser(user);
        userMapper.updateById(user);
    }

    @Override
    public int countAll() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return userMapper.selectCount(wrapper);
    }

    // 删除redis缓存
    @Override
    public void delRedisUser(User user) {

    }
}
