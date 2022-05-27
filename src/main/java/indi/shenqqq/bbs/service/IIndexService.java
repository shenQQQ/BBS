package indi.shenqqq.bbs.service;
/**
 * @Author Shen Qi
 * @Date 2022/5/16 10:56
 * @Description XX
 */
public interface IIndexService {
    // 索引全部话题
    void indexAllArticle();

    // 索引话题
    void indexArticle(String id, String title, String content,String head_img);

    // 删除话题索引
    void deleteArticleIndex(String id);

    // 删除所有话题索引
    void batchDeleteIndex();
}
