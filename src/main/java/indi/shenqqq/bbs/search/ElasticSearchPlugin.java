package indi.shenqqq.bbs.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.dao.ArticleMapper;
import indi.shenqqq.bbs.model.Article;
import org.apache.kafka.common.internals.Topic;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Shen Qi
 * @Date 2022/5/16 13:38
 * @Description XX
 */
@Component
@Aspect
public class ElasticSearchPlugin {

    @Resource
    private ElasticSearchService elasticSearchService;
    @Resource
    private ArticleMapper articleMapper;

    @Around("indi.shenqqq.bbs.pointcut.ArticleServicePointcut.search()")
    public Object search(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        if (elasticSearchService.instance() == null) return proceedingJoinPoint.proceed(args);
        Page<Map<String, Object>> page = elasticSearchService.searchDocument((Integer) args[0], (Integer) args[1], (String) args[2], "title", "content");
        System.out.println(page.getRecords());
        return page;
    }

    @After("indi.shenqqq.bbs.pointcut.IndexServicePointcut.indexAllArticle()")
    public void indexAllArticle() {
        if (elasticSearchService.instance() != null) {
            List<Article> articles = articleMapper.selectList(null);
            System.out.println("共需索引" + articles.size() + "个索引");
            int i = 0;
            Map<String, Map<String, Object>> sources = articles.stream().collect(Collectors.toMap(key -> String.valueOf(key
                    .getId()), value -> {
                System.out.println("正在索引第" + value.getId() +"个文章");
                Map<String, Object> map = new HashMap<>();
                map.put("title", value.getTitle());
                map.put("head_img",value.getHeadImg());
                map.put("content", value.getContent());
                return map;
            }));
            elasticSearchService.bulkDocument("article", sources);
        }
    }

    @After("indi.shenqqq.bbs.pointcut.IndexServicePointcut.indexArticle()")
    public void indexArticle(JoinPoint joinPoint) {
        if (elasticSearchService.instance() != null) {
            Object[] args = joinPoint.getArgs();
            Map<String, Object> source = new HashMap<>();
            source.put("title", args[1]);
            source.put("content", args[2]);
            source.put("head_img",args[3]);
            elasticSearchService.createDocument("article", (String) args[0], source);
        }
    }

    @After("indi.shenqqq.bbs.pointcut.IndexServicePointcut.deleteArticleIndex()")
    public void deleteTopicIndex(JoinPoint joinPoint) {
        if (elasticSearchService.instance() != null) {
            elasticSearchService.deleteDocument("article", (String) joinPoint.getArgs()[0]);
        }
    }

    @After("indi.shenqqq.bbs.pointcut.IndexServicePointcut.batchDeleteIndex()")
    public void batchDeleteIndex(JoinPoint joinPoint) {
        if (elasticSearchService.instance() != null) {
            List<Article> articles = articleMapper.selectList(null);
            List<Integer> ids = articles.stream().map(Article::getId).collect(Collectors.toList());
            elasticSearchService.bulkDeleteDocument("article", ids);
        }
    }
}
