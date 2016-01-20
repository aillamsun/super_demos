package kafka.demo1.consumer;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by sungang on 2016/1/11.16:10
 */
public class MTConsumer {
    private ExecutorService executor;
    private final ConsumerConnector consumer;
    private final String topic;

    public MTConsumer(String zookeeper, String groupId, String topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(zookeeper, groupId));
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }

    public void shutdown() {
        if (consumer != null)
            consumer.shutdown();
        if (executor != null)
            executor.shutdown();
    }

    public void testMultiThreadConsumer(int threadCount) {

        Map<String, Integer> topicMap = new HashMap<String, Integer>();

        // Define thread count for each topic
        topicMap.put(topic, threadCount);

        // Here we have used a single topic but we can also add
        // multiple topics to topicCount MAP
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams =
                consumer.createMessageStreams(topicMap);

        // Launching the thread pool
        executor = Executors.newFixedThreadPool(threadCount);

        for (final List<KafkaStream<byte[], byte[]>> streams: topicMessageStreams.values()) {
            for (final KafkaStream<byte[], byte[]> stream: streams) {
                executor.submit(new MessageHandler(stream));
            }
        }
    }

    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Usage: MTConsumer [zookeeper] [groupId] [topic] [threadsCount]");
            System.exit(1);
        }

        String zooKeeper = args[0];
        String groupId = args[1];
        String topic = args[2];
        int threadCount = Integer.parseInt(args[3]);
        MTConsumer multiThreadHLConsumer =
                new MTConsumer(zooKeeper, groupId, topic);
        multiThreadHLConsumer.testMultiThreadConsumer(threadCount);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
            // ignore
        }
        multiThreadHLConsumer.shutdown();

    }
}
