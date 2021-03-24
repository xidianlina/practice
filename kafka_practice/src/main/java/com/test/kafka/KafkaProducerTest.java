package com.test.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

import static com.test.kafka.KafkaProperties.KafkaServerURL;
import static com.test.kafka.KafkaProperties.KafkaTopic;

public class KafkaProducerTest extends Thread {
    static Logger log = Logger.getLogger(KafkaProducerTest.class);

    private static KafkaProducer<String, String> producer = null;

    //初始化生产者
    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
    }

    //初始化配置
    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerURL);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    @Override
    public void run() {
        //消息实体
        ProducerRecord<String, String> record = null;
        while (true) {
            record = new ProducerRecord<String, String>(KafkaTopic, "value" + (int) (10 * (Math.random())));
            //发送消息
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e) {
                        log.info("send error" + e.getMessage());
                    } else {
                        System.out.println(String.format("offset:%s,partition:%s", recordMetadata.offset(), recordMetadata.partition()));
                    }
                }
            });
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            producer.close();
        }
    }
}
