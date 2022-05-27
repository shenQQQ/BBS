package indi.shenqqq.bbs.cache;

import indi.shenqqq.bbs.config.Config;
import indi.shenqqq.bbs.service.ISystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 12:10
 * @Description XX
 */
@Component
@Slf4j
public class RedisService {

    @Resource
    ISystemConfigService systemConfigService;

    @PostConstruct
    @DependsOn("flywayConfig")
    public void RedisService(){
        String host = systemConfigService.selectByKey("redis_host");
        String port = systemConfigService.selectByKey("redis_port");
        String password = StringUtils.isEmpty(systemConfigService.selectByKey("redis_password")) ? null : systemConfigService.selectByKey("redis_password");
        String database = systemConfigService.selectByKey("redis_database");
        String timeout = systemConfigService.selectByKey("redis_timeout");

        if (!(!StringUtils.isEmpty(host) && !StringUtils.isEmpty(port) && !StringUtils.isEmpty(database) && !StringUtils
                .isEmpty(timeout))) {
            log.info("redis配置信息不全或没有配置...");
        }
    }

    private JedisPool jedisPool;

    public void setJedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool instance() {
        try {
            if (this.jedisPool != null) return this.jedisPool;
            String host = systemConfigService.selectByKey("redis_host");
            String port = systemConfigService.selectByKey("redis_port");
            String password = StringUtils.isEmpty(systemConfigService.selectByKey("redis_password")) ? null : systemConfigService.selectByKey("redis_password");
            String database = systemConfigService.selectByKey("redis_database");
            String timeout = systemConfigService.selectByKey("redis_timeout");

            if (!this.isRedisConfig()) {
                return null;
            }
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            // 配置jedis连接池最多空闲多少个实例，源码默认 8
            jedisPoolConfig.setMaxIdle(7);
            // 配置jedis连接池最多创建多少个实例，源码默认 8
            jedisPoolConfig.setMaxTotal(20);
            //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            jedisPoolConfig.setTestOnBorrow(true);
            //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
            jedisPoolConfig.setTestOnReturn(true);
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port), Integer.parseInt(timeout), password,
                    Integer.parseInt(database));
            Jedis jedis = jedisPool.getResource();
            jedis.close();
            log.info("redis连接对象获取成功...");

            return this.jedisPool;
        } catch (Exception e) {
            log.error("配置redis连接池报错，错误信息: {}", e.getMessage());
            return null;
        }
    }

    public boolean isRedisConfig() {
        String host = systemConfigService.selectByKey("redis_host");
        String port = systemConfigService.selectByKey("redis_port");
        String database = systemConfigService.selectByKey("redis_database");
        String timeout = systemConfigService.selectByKey("redis_timeout");
        return !StringUtils.isEmpty(host) && !StringUtils.isEmpty(port) && !StringUtils.isEmpty(database) && !StringUtils
                .isEmpty(timeout);
    }

    public String getString(String key) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || instance == null) return null;
        Jedis jedis = instance.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public void setString(String key, String value) {
        this.setString(key, value, 300); // 如果不指定过时时间，默认为5分钟
    }

    public void setString(String key, String value, int expireTime) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || instance == null) return;
        Jedis jedis = instance.getResource();
        SetParams params = new SetParams();
        params.px(expireTime * 1000);
        jedis.set(key, value, params);
        jedis.close();
    }

    public void delString(String key) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || instance == null) return;
        Jedis jedis = instance.getResource();
        jedis.del(key); // 返回值成功是 1
        jedis.close();
    }

    // TODO 后面有需要会补充获取 list, map 等方法

}
