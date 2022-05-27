package indi.shenqqq.bbs.mq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.shenqqq.bbs.model.User;
import indi.shenqqq.bbs.service.INotificationService;
import indi.shenqqq.bbs.service.ISystemConfigService;
import indi.shenqqq.bbs.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @Author Shen Qi
 * @Date 2022/5/17 15:53
 * @Description XX
 */
@Slf4j
@Component
public class Consumer {
    @Resource
    ISystemConfigService systemConfigService;
    @Resource
    IUserService userService;
    @Resource
    INotificationService notificationService;

    Properties properties = new Properties();

    public boolean instance() {
        String ip = systemConfigService.selectByKey("kafka_ip");
        if (StringUtils.isEmpty(ip)) return false;
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ip);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "bbs-consumer-group1");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//重置消费者offset的方法（达到重复消费的目的），设置该属性也只在两种情况下生效：1.上面设置的消费组还未消费(可以更改组名来消费)2.该offset已经过期
        return true;
    }

    public void receive(int count) {
        int total = userService.countAll();
        if (!instance()) {
            log.info("消息队列配置有问题");
            return;
        }
        KafkaConsumer<String, String> consumer = null;
        try {
            consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Collections.singletonList("message")); //Arrays.asList()
            while (count > 0) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                for (ConsumerRecord consumerRecord : consumerRecords) {
                    String s = consumerRecord.value().toString();
                    String[] str = s.split(",");
                    User user = userService.selectByUsername(str[0]);
                    notificationService.save(0, user.getId(), 0, "broadcast", str[1]);
                    count--;
                    log.info("还有" + count + "条消息未处理，本条消息为：" + s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }
}
