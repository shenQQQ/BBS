package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.shenqqq.bbs.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}