package cn.diyiliu.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

/**
 * KafkaConfig
 *
 * @author: DIYILIU
 * @date: 2022/03/03
 */

@Configuration
@ComponentScan("cn.diyiliu.kafka")
@PropertySource("classpath:application.properties")
public class KafkaConfig {

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    public static class MessageProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

//        @Autowired
//        private KafkaTemplate<String, Greeting> greetingKafkaTemplate;

        @Value(value = "${message.topic.name}")
        private String topicName;

//        @Value(value = "${partitioned.topic.name}")
//        private String partitionedTopicName;
//
//        @Value(value = "${filtered.topic.name}")
//        private String filteredTopicName;
//
//        @Value(value = "${greeting.topic.name}")
//        private String greetingTopicName;

        public void sendMessage(String message) {

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

//            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//                @Override
//                public void onSuccess(SendResult<String, String> result) {
//                    System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
//                            .offset() + "]");
//                }
//
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
//                }
//            });
        }

//        public void sendMessageToPartition(String message, int partition) {
//            kafkaTemplate.send(partitionedTopicName, partition, null, message);
//        }
//
//        public void sendMessageToFiltered(String message) {
//            kafkaTemplate.send(filteredTopicName, message);
//        }
//
//        public void sendGreetingMessage(Greeting greeting) {
//            greetingKafkaTemplate.send(greetingTopicName, greeting);
//        }
    }

    public static class MessageListener {
        public CountDownLatch latch = new CountDownLatch(3);

        public CountDownLatch partitionLatch = new CountDownLatch(2);

        public CountDownLatch filterLatch = new CountDownLatch(2);

        public CountDownLatch greetingLatch = new CountDownLatch(1);

        @KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
        public void listenGroupFoo(String message) {
            System.out.println(LocalDateTime.now() + " Received Message in group 'foo': " + message);
            latch.countDown();
        }

//        @KafkaListener(topics = "${message.topic.name}", groupId = "bar", containerFactory = "barKafkaListenerContainerFactory")
//        public void listenGroupBar(String message) {
//            System.out.println("Received Message in group 'bar': " + message);
//            latch.countDown();
//        }
//
//        @KafkaListener(topics = "${message.topic.name}", containerFactory = "headersKafkaListenerContainerFactory")
//        public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//            System.out.println("Received Message: " + message + " from partition: " + partition);
//            latch.countDown();
//        }
//
//        @KafkaListener(topicPartitions = @TopicPartition(topic = "${partitioned.topic.name}", partitions = {"0", "3"}), containerFactory = "partitionsKafkaListenerContainerFactory")
//        public void listenToPartition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//            System.out.println("Received Message: " + message + " from partition: " + partition);
//            this.partitionLatch.countDown();
//        }
//
//        @KafkaListener(topics = "${filtered.topic.name}", containerFactory = "filterKafkaListenerContainerFactory")
//        public void listenWithFilter(String message) {
//            System.out.println("Received Message in filtered listener: " + message);
//            this.filterLatch.countDown();
//        }
//
//        @KafkaListener(topics = "${greeting.topic.name}", containerFactory = "greetingKafkaListenerContainerFactory")
//        public void greetingListener(Greeting greeting) {
//            System.out.println("Received greeting message: " + greeting);
//            this.greetingLatch.countDown();
//        }
    }
}
