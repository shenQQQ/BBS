package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.Ad;

/**
 * @Author Shen Qi
 * @Date 2022/4/22 18:40
 * @Description XX
 */
public interface IAdService {
    public Ad selectAd();
    public int updateAd(Ad ad);
}
