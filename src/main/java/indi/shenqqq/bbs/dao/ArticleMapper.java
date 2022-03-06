package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 17:54
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    Page<Map<String, Object>> selectAll(Page<Map<String, Object>> iPage);

    Page<Map<String, Object>> selectByUserId(Page<Map<String, Object>> iPage, Integer userId);

}