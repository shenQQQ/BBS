package indi.shenqqq.bbs.mq;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @Author Shen Qi
 * @Date 2022/4/14 14:56
 * @Description XX
 */
public class TestConsumer {
    public static void main(String[] args) {//自动提交
        //1.创建消费者配置信息
        Properties properties = new Properties();
        //链接的集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.3.8.118:9092");
        //开启自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        //自动提交的延迟
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //key,value的反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test-consumer-group1");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");//重置消费者offset的方法（达到重复消费的目的），设置该属性也只在两种情况下生效：1.上面设置的消费组还未消费(可以更改组名来消费)2.该offset已经过期


        //创建消费者
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("study")); //Arrays.asList()

        while (true) {
            //获取数据
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);

            //解析并打印consumerRecords
            for (ConsumerRecord consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key() + "----" + consumerRecord.value());
            }
        }

        //consumer无需close()
    }
}
