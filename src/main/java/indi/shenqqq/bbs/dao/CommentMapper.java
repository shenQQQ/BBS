package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 17:56
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    Page<Map<String, Object>> selectByUserId(Page<Map<String, Object>> iPage, @Param("userId") Integer userId);

}
