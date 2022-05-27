package indi.shenqqq.bbs.service;

import indi.shenqqq.bbs.model.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/21 15:17
 * @Description XX
 */
public interface ISystemConfigService {
    Map<String, SystemConfig> selectAllConfig();

    String selectByKey(String key);

    void update(Map<String, String> map) ;

    void updateByKey(String key, SystemConfig systemConfig);
}
