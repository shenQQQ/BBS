package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:08
 * @Description XX
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    Page<Map<String, Object>> selectArticleByTagId(Page<Map<String, Object>> iPage, @Param("tagId") Integer tagId);

    List<Tag> selectTagByArticleCount(@Param("topTagNum") Integer topTagNum);

}
