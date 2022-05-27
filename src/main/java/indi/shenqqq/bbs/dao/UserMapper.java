package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 15:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Page<Map<String, Object>> selectAll(Page<Map<String, Object>> iPage);

    Page<Map<String, Object>> search(Page<Map<String, Object>> iPage, @Param("keyword") String keywords);
}