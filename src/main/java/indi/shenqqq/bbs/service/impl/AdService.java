package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.shenqqq.bbs.dao.AdMapper;
import indi.shenqqq.bbs.model.Ad;
import indi.shenqqq.bbs.service.IAdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Shen Qi
 * @Date 2022/4/22 18:40
 * @Description XX
 */
@Service
public class AdService implements IAdService {

    @Resource
    private AdMapper adMapper;

    @Override
    public Ad selectAd() {
        return adMapper.selectById(1);
    }

    @Override
    public int updateAd(Ad ad) {
        QueryWrapper<Ad> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Ad::getId, 1);
        return adMapper.update(ad,wrapper);
    }
}
