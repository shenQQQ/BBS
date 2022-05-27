package indi.shenqqq.bbs.service.impl;

import indi.shenqqq.bbs.dao.RecommendMapper;
import indi.shenqqq.bbs.model.Recommend;
import indi.shenqqq.bbs.model.vo.RecommendVo;
import indi.shenqqq.bbs.service.IArticleService;
import indi.shenqqq.bbs.service.IRecommendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/4/22 19:01
 * @Description XX
 */
@Service
@Transactional
public class RecommendService implements IRecommendService {

    @Resource
    RecommendMapper recommendMapper;
    @Resource
    IArticleService articleService;

    @Override
    public List<RecommendVo> selectVo() {
        List<Recommend> list = recommendMapper.selectList(null);
        List<RecommendVo> result = new ArrayList<>();
        for (Recommend recommend : list) {
            result.add(new RecommendVo(recommend,articleService.selectById(recommend.getArticleId()).getHeadImg()));
        }
        return result;
    }

    @Override
    public String selectStr() {
        List<Recommend> list = recommendMapper.selectList(null);
        StringBuffer result = new StringBuffer();
        for (Recommend recommend : list) {
            result.append(recommend.getArticleId() + ",");
        }
        return result.substring(0,result.length()-1);
    }

    @Override
    public void update(List<Recommend> list) {
        recommendMapper.delete(null);
        for (Recommend recommend : list) {
            recommendMapper.insert(recommend);
        }
    }
}
