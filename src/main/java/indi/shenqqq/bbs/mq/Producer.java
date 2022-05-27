package indi.shenqqq.bbs.mq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.service.ISystemConfigService;
import indi.shenqqq.bbs.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

/**
 * @Author Shen Qi
 * @Date 2022/5/17 15:16
 * @Description XX
 */
@Slf4j
@Component
public class Producer {

    @Resource
    ISystemConfigService systemConfigService;
    @Resource
    IUserService userService;

    Properties properties = new Properties();

    public boolean instance() {
        String ip = systemConfigService.selectByKey("kafka_ip");
        if (StringUtils.isEmpty(ip)) return false;
        properties.put("bootstrap.servers", ip);
        properties.put("retries", 1);
        properties.put("batch.size", 16384);//16k
        properties.put("linger.ms", 10);
        properties.put("buffer.memory", 33554432);//32m
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return true;
    }

    public int broadcast(String content) {
        int total = userService.countAll();
        int size = 50;
        int totalPage = total / size + 1;
        int count = 0;
        if (!instance()) {
            log.info("消息队列配置有问题");
            return count;
        }
        KafkaProducer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
            for (int i = 1; i <= totalPage; i++) {
                Page<Map<String, Object>> userPage = userService.selectAll(i, size);
                for (Map<String, Object> record : userPage.getRecords()) {
                    producer.send(new ProducerRecord<String, String>("message", record.get("username").toString() + "," + content));
                    log.info("发送给"+record.get("username").toString()+"的广播消息送到了消息队列里");
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            return count;
        }
    }
}
