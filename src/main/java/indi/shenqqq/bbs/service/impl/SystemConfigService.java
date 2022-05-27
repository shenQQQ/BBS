package indi.shenqqq.bbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.shenqqq.bbs.dao.SystemConfigMapper;
import indi.shenqqq.bbs.model.SystemConfig;
import indi.shenqqq.bbs.service.ISystemConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/21 15:18
 * @Description XX
 */
@Service
@Transactional
public class SystemConfigService implements ISystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    private static Map<String, SystemConfig> SYSTEM_CONFIG = new LinkedHashMap<>();

    @PostConstruct
    @DependsOn("flywayConfig")
    private void init(){
        System.out.println("系统配置初始化");
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        Map<String,SystemConfig> cache = new LinkedHashMap<>();
        for (SystemConfig systemConfig : systemConfigs) {
            cache.put(systemConfig.getKey(),systemConfig);
        }
        SYSTEM_CONFIG = cache;
        System.out.println(SYSTEM_CONFIG);
    }

    @Override
    public Map<String, SystemConfig> selectAllConfig() {
        if (SYSTEM_CONFIG.size() != 0) return SYSTEM_CONFIG;
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        Map<String,SystemConfig> cache = new LinkedHashMap<>();
        for (SystemConfig systemConfig : systemConfigs) {
            cache.put(systemConfig.getKey(),systemConfig);
        }
        SYSTEM_CONFIG = cache;
        return SYSTEM_CONFIG;
    }

    @Override
    public String selectByKey(String key) {
        if (SYSTEM_CONFIG.size() != 0) return SYSTEM_CONFIG.get(key).getValue();
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemConfig::getKey, key);
        return systemConfigMapper.selectOne(wrapper).getValue();
    }

    @Override
    public void update(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setKey(key);
            systemConfig.setValue(value);
            QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SystemConfig::getKey, systemConfig.getKey());
            systemConfigMapper.update(systemConfig, wrapper);
        }
        flushCache();
    }

    public void flushCache(){
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        Map<String,SystemConfig> cache = new LinkedHashMap<>();
        for (SystemConfig systemConfig : systemConfigs) {
            cache.put(systemConfig.getKey(),systemConfig);
        }
        SYSTEM_CONFIG = cache;
        System.out.println(SYSTEM_CONFIG);
    }

    @Override
    public void updateByKey(String key, SystemConfig systemConfig) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemConfig::getKey, key);
        systemConfigMapper.update(systemConfig, wrapper);
        SYSTEM_CONFIG = new HashMap<>();
        selectAllConfig();
    }
}
