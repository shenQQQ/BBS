package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.Tag;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:24
 * @Description XX
 */
public interface ITagService {
    void selectTagsByArticleId(Page<Map<String, Object>> page);

    Tag selectById(Integer id);

    Tag selectByName(String name);

    List<Tag> selectByIds(List<Integer> ids);

    List<Tag> selectByArticleId(Integer topicId);

    List<Tag> insertTag(String newTags);

    List<Tag> selectTagByArticleCount(int num);

    Page<Map<String, Object>> selectArticleByTagId(Integer tagId, Integer pageNo);

    IPage<Tag> selectAll(Integer pageNo, Integer pageSize);

    void update(Tag tag);
}
