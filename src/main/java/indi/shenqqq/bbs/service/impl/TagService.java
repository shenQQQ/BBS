package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.dao.TagMapper;
import indi.shenqqq.bbs.model.ArticleTag;
import indi.shenqqq.bbs.model.Tag;
import indi.shenqqq.bbs.service.IArticleTagService;
import indi.shenqqq.bbs.service.ITagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Shen Qi
 * @Date 2022/4/11 18:30
 * @Description XX
 */
@Service
@Transactional
public class TagService implements ITagService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private IArticleTagService articleTagService;

    @Override
    public void selectTagsByArticleId(Page<Map<String, Object>> page) {
        page.getRecords().forEach(map -> {
            List<ArticleTag> articleTags = articleTagService.selectByArticleId((Integer) map.get("id"));
            if (!articleTags.isEmpty()) {
                List<Integer> tagIds = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = this.selectByIds(tagIds);
                map.put("tags", tags);
            }
        });
    }

    @Override
    public Tag selectById(Integer id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Tag selectByName(String name) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tag::getName, name);
        return tagMapper.selectOne(wrapper);
    }

    @Override
    public List<Tag> selectByIds(List<Integer> ids) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Tag::getId, ids);
        return tagMapper.selectList(wrapper);
    }

    // 根据话题查询关联的所有标签
    @Override
    public List<Tag> selectByArticleId(Integer articleId) {
        List<ArticleTag> articleTags = articleTagService.selectByArticleId(articleId);
        if (!articleTags.isEmpty()) {
            List<Integer> tagIds = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.lambda().in(Tag::getId, tagIds);
            return tagMapper.selectList(wrapper);
        }
        return new ArrayList<Tag>();
    }

    // 将创建话题时填的tag处理并保存
    @Override
    public List<Tag> insertTag(String newTags) {
        // 使用工具将字符串按逗号分隔成数组
        System.out.println(newTags);
        String[] _tags = StringUtils.commaDelimitedListToStringArray(newTags);
        List<Tag> tagList = new ArrayList<>();
        for (String _tag : _tags) {
            _tag = _tag.trim();
            if(StringUtils.isEmpty(_tag)){continue;}
            Tag tag = this.selectByName(_tag);
            if (tag == null) {
                tag = new Tag();
                tag.setName(_tag);
                tag.setInTime(new Date());
                tagMapper.insert(tag);
            }
            tagList.add(tag);
        }
        System.out.println(tagList);
        return tagList;
    }

    @Override
    public List<Tag> selectTagByArticleCount(int num) {
        return tagMapper.selectTagByArticleCount(num);
    }


    // 查询标签关联的话题
    @Override
    public Page<Map<String, Object>> selectArticleByTagId(Integer tagId, Integer pageNo) {
        Page<Map<String, Object>> iPage = new Page<>(pageNo, Config.INDEX_PAGE_ARTICLE_NUM);
        return tagMapper.selectArticleByTagId(iPage, tagId);
    }

    @Override
    public Page<Map<String, Object>> selectAllTag(Integer pageNo) {
        Page<Map<String, Object>> iPage = new Page<>(pageNo, 10);
        return tagMapper.selectAllTag(iPage);
    }

    @Override
    public Page<Map<String, Object>> search(Integer pageNo, String keyword) {
        Page<Map<String, Object>> iPage = new Page<>(pageNo, 10);
        return tagMapper.search(iPage,keyword);
    }

    // 查询标签列表
    @Override
    public IPage<Tag> selectAll(Integer pageNo, Integer pageSize) {
        IPage<Tag> iPage = new Page<>(pageNo, pageSize == null ? Config.TAG_PAGE_SIZE : pageSize);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        return tagMapper.selectPage(iPage, wrapper);
    }

    @Override
    public void delete(Integer id) {
        articleTagService.deleteByTagId(id);
        tagMapper.deleteById(id);
    }

    @Override
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }


}
