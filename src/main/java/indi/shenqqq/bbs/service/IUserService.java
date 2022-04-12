package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.shenqqq.bbs.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 16:51
 * @Description XX
 */
public interface IUserService {
    User selectByUsername(String username);

    User selectByToken(String token);

    User selectByEmail(String email);

    User selectById(Integer id);

    List<User> selectTopList(Integer limit);

    User addUser(String username,
                 String password,
                 String avator,
                 String email,
                 String bio);

    void save(User user);

    void deleteUser(Integer id);

    void update(User user);

    int countAll();

    void delRedisUser(User user);
}
