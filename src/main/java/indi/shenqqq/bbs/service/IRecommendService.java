package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.Recommend;
import indi.shenqqq.bbs.model.vo.RecommendVo;

import java.util.List;

/**
 * @Author Shen Qi
 * @Date 2022/4/22 19:01
 * @Description XX
 */
public interface IRecommendService {
    List<RecommendVo> selectVo();
    String selectStr();
    void update(List<Recommend> list);
}
