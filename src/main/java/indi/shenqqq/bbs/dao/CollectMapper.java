package indi.shenqqq.bbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Collect;
import indi.shenqqq.bbs.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/1 17:55
 */
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {

    Page<Map<String, Object>> selectByUserId(Page<Map<String, Object>> page, Integer userId);
}
