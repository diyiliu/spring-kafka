package cn.diyiliu.kafka;

import cn.diyiliu.kafka.config.Greeting;
import cn.diyiliu.kafka.config.KafkaConfig;
import cn.diyiliu.kafka.config.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Main
 *
 * @author: DIYILIU
 * @date: 2022/03/03
 */
public class Main {

    public static void main(String[] args) throws Exception{
        ApplicationContext context = SpringContextHolder.run(KafkaConfig.class);

        KafkaConfig.MessageProducer producer = context.getBean(KafkaConfig.MessageProducer.class);
        KafkaConfig.MessageListener listener = context.getBean(KafkaConfig.MessageListener.class);

        /*
         * Sending a Hello World message to topic 'baeldung'.
         * Must be received by both listeners with group foo
         * and bar with containerFactory fooKafkaListenerContainerFactory
         * and barKafkaListenerContainerFactory respectively.
         * It will also be received by the listener with
         * headersKafkaListenerContainerFactory as container factory.
         */
        while (true) {
            producer.sendMessage("Hello, World! " + UUID.randomUUID());
        }
//        listener.latch.await(10, TimeUnit.SECONDS);

//        /*
//         * Sending message to a topic with 5 partitions,
//         * each message to a different partition. But as per
//         * listener configuration, only the messages from
//         * partition 0 and 3 will be consumed.
//         */
//        for (int i = 0; i < 5; i++) {
//            producer.sendMessageToPartition("Hello To Partitioned Topic!", i);
//        }
//        listener.partitionLatch.await(10, TimeUnit.SECONDS);
//
//        /*
//         * Sending message to 'filtered' topic. As per listener
//         * configuration,  all messages with char sequence
//         * 'World' will be discarded.
//         */
//        producer.sendMessageToFiltered("Hello Baeldung!");
//        producer.sendMessageToFiltered("Hello World!");
//        listener.filterLatch.await(10, TimeUnit.SECONDS);
//
//        /*
//         * Sending message to 'greeting' topic. This will send
//         * and received a java object with the help of
//         * greetingKafkaListenerContainerFactory.
//         */
//        producer.sendGreetingMessage(new Greeting("Greetings", "World!"));
//        listener.greetingLatch.await(10, TimeUnit.SECONDS);
    }

}
