package indi.shenqqq.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.javafx.image.ByteToBytePixelConverter;
import indi.shenqqq.bbs.model.Article;
import indi.shenqqq.bbs.model.User;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/3/2 12:56
 * @Description XX
 */
public interface IArticleService {

    Article selectById(int id);

    Article selectByTitle(String title);

    Page<Map<String, Object>> selectAll(int pageNo, int pageSize);

    Page<Map<String, Object>> selectByUserId(int userId, int pageNo, int pageSize);

    void save(String title, String content, String headImg, User user);

    void update(Article article);

    void delete(Article article);

    int countAll();

    Page<Map<String, Object>> search(Integer pageNo, Integer pageSize, String keyword);

}
